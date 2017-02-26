package ca.effacious.learn.gcp.spark.commoncrawl

import ca.effacious.learn.gcp.spark.AppBase
import ca.effacious.learn.gcp.spark.WikiLinkAppMain.{args, getClass}
import ca.effacious.learn.gcp.spark.wikilink.Dataprep
import org.slf4j.LoggerFactory

/**
  * Used for unzip common crawl data warc gzip file.
  *
  */
class CommonCrawlAppUnzip extends AppBase {

  lazy val c_input_file_path = {
    parseCommandLineArguments("-input_file_path", c_CommandLineArgsList
      , "argument [input_file_path] is missing; this is path to the input file to process.")
  }

  lazy val c_output_file_path = {
    parseCommandLineArguments("-output_file_path", c_CommandLineArgsList
      , "argument [output_file_path] is missing; this is path where the file will be unzipped.")
  }

  @transient override lazy val LOGR = LoggerFactory.getLogger(getClass.getName)

  def get_app_name = "CommonCrawlAppUnzip"

  def MAIN(a_AppName :String ,a_commandLineArguments: Seq[String]) = {

    initialize(a_commandLineArguments)
    initializeSparkContext("")

    val data_fl_rdd= c_sc..textFile(c_input_file_path)
    val row_count = data_fl_rdd.count()

    data_fl_rdd.map(x=> {
      x
    }).saveAsTextFile(c_output_file_path)


  }
}


object CommonCrawlAppUnzipMain extends App{
  @transient lazy val LOGR = LoggerFactory.getLogger(getClass.getName)

  //override def main(p_commandLineArguments: Array[String]) = {
  val app_instance = new CommonCrawlAppUnzip
  app_instance.initialize(args.toList)
  app_instance.MAIN("CommonCrawlAppUnzip", args.toList)

  if (LOGR.isInfoEnabled)
    LOGR.info("Finished ")
  // }
}
