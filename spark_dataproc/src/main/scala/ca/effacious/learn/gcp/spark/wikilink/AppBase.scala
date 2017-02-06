package ca.effacious.learn.gcp.spark


import java.io.{File, FileNotFoundException}
import java.util.Date

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.time.DateFormatUtils
import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{LoggerFactory, MDC}

/**
  * Thrown in case a command line argument is missing
  *
  * @param comandLineArg
  */
case class MissingCommandLineArgException(comandLineArg:String)  extends Exception(comandLineArg)

case class ConfigurationNotFoundException(configuration:String)  extends Exception(configuration)

/**
  * Base class for application jobs
  */
trait AppBase {
  lazy val LOGR = LoggerFactory.getLogger(getClass.getName)

  lazy val APP_NAME = get_app_name() //'DUMMY

  /**
    * This is a random uniquely generated key that is used to link all log records related to a particular run of
    * the program.
    */
  lazy val RUN_KEY = {
    val runLogKey = RandomStringUtils.randomAlphabetic(3) + DateFormatUtils.format(new Date, "yyyyMMdd")
    MDC.put("MDC$RUN_LOG_KEY", runLogKey)
    runLogKey
  }

  /**
    * The scheduler task name under which this task is running as
    */
  lazy val c_scheduler_task_name = {
    parseCommandLineArguments("-scheduler_task_name", c_CommandLineArgsList
      , "argument [scheduler_task_name] is missing; this is name of the scheduler task, that is executing this program.")
  }

  var c_sc: SparkContext = _
  var c_sqlContext: SQLContext = _

  /**
    * Command line argument
    */
  var c_CommandLineArgsList :Seq[String] = _

  /**
    * Generic application configuration file
    */
  var c_appConf :Config = _


  def initialize(a_commandLineArgsList :Seq[String]):Unit = {
    LOGR.info("Initializing ...")
    LOGR.info("Command line : {}",a_commandLineArgsList.mkString(","))
    c_CommandLineArgsList = a_commandLineArgsList

    val appEnvironmentalConfigFilePath = {parseCommandLineArguments("-appEnvConf", c_CommandLineArgsList
      ,"argument [appEnvConf] is missing; this is the configuration file containing all the environment specific related settings.", true)}

    val appEnvConf = ConfigFactory.parseFile(new java.io.File(appEnvironmentalConfigFilePath))

    /*
    * The following provides a way to override the default : application_conf
    * URL : https://typesafehub.github.io/config/latest/api/com/typesafe/config/Config.html
    *
    *
    */
    c_appConf = ConfigFactory.load().withFallback(appEnvConf).resolve()

  }

  /**
    * Used for retrieving a specific parameter from the command line arguments
    *
    * @param a_configurationParam
    * @param a_argsList
    * @return
    */
  def parseCommandLineArguments(a_configurationParam: String, a_argsList: Seq[String], message: String = "argument not present"
                                , throwException: Boolean = false, defaultVal: String = null): String = {


    if ((a_argsList == null) && StringUtils.isNotBlank(defaultVal))
      return defaultVal

    if(a_argsList.contains(a_configurationParam) == false) {
      if(throwException)
        throw new MissingCommandLineArgException(message)

      LOGR.info(message)
      return defaultVal
    }

    a_argsList match {
      case x :: value :: tail if (x == a_configurationParam) =>  value

      case unknownConfig :: value :: tail =>
        LOGR.trace(s"ignoring current config ${unknownConfig}, when checking for ${a_configurationParam} ");
        parseCommandLineArguments(a_configurationParam,tail)
    }

  }

  def getSparkConf(a_appName: String): SparkConf = {

    val spark_master_config = {parseCommandLineArguments("-spark_master", c_CommandLineArgsList
      ,"The master url for spark context, defaults to local if not supplied", false ,"local")}

    val l_sconf = new SparkConf(true).setAppName(a_appName)

    //The spark_master_config gives an option for us to invoke setmaster or as per environment option.

    spark_master_config match {
      case "local" => l_sconf.setMaster(spark_master_config)
      case _ => l_sconf
    }
  }

  /**
    * Sets the spark contexts
    *
    */
  def initializeSparkContext(a_appName: String = APP_NAME) = {
    if (LOGR.isInfoEnabled)
      LOGR.info("Initializing spark context ...")

    val sparkConf = getSparkConf(a_appName)
    c_sc = new SparkContext(sparkConf)
    //c_sqlContext = new org.apache.spark.sql.SQLContext(c_sc)
  }

  def stopSparkContext(): Unit = {
    if (LOGR.isInfoEnabled())
      LOGR.info("Stopping spark context ....")

    c_sc.stop()
  }

  def setContextObjects(a_sc: SparkContext, a_sqlContext: SQLContext): Unit = {
    c_sc = a_sc
    c_sqlContext = a_sqlContext
  }

  def get_app_name():String
}
