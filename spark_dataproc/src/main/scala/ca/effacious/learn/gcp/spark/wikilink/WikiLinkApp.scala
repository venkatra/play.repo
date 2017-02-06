package ca.effacious.learn.gcp.spark

import org.slf4j.LoggerFactory


class WikiLinkApp extends AppBase {

  lazy val c_input_file_path = {
    parseCommandLineArguments("-input_file_path", c_CommandLineArgsList
      , "argument [input_file_path] is missing; this is path to the input file to process.")
  }

  @transient override lazy val LOGR = LoggerFactory.getLogger(getClass.getName)

  def get_app_name = "WikiLinkApp"

  def MAIN(a_AppName :String ,a_commandLineArguments: Seq[String]) = {

    initialize(a_commandLineArguments)
    initializeSparkContext("")

    val data_fl_rdd= c_sc.textFile(c_input_file_path)
    val row_count = data_fl_rdd.count()

    LOGR.info(s"Total record count : {}",row_count)


  }
}

object WikiLinkAppMain extends App{
  @transient lazy val LOGR = LoggerFactory.getLogger(getClass.getName)

  //override def main(p_commandLineArguments: Array[String]) = {
    val app_instance = new WikiLinkApp
    app_instance.initialize(args.toList)
    app_instance.MAIN("WikiLinkApp", args.toList)

    if (LOGR.isInfoEnabled)
      LOGR.info("Finished ")
 // }
}
