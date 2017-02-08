package ca.effacious.learn.gcp.spark

import ca.effacious.learn.gcp.spark.wikilink.Dataprep
import org.slf4j.LoggerFactory


class WikiLinkApp extends AppBase {

  lazy val c_input_file_path = {
    parseCommandLineArguments("-input_file_path", c_CommandLineArgsList
      , "argument [input_file_path] is missing; this is path to the input file to process.")
  }

  lazy val c_output_file_path = {
    parseCommandLineArguments("-output_file_path", c_CommandLineArgsList
      , "argument [output_file_path] is missing; this is path to the input file to process.")
  }

  @transient override lazy val LOGR = LoggerFactory.getLogger(getClass.getName)

  def get_app_name = "WikiLinkApp"

  def MAIN(a_AppName :String ,a_commandLineArguments: Seq[String]) = {

    initialize(a_commandLineArguments)
    initializeSparkContext("")

    val data_fl_rdd= c_sc.textFile(c_input_file_path)
    val row_count = data_fl_rdd.count()

    val dataprep = new Dataprep
    val mapop = data_fl_rdd.filter(_.trim.length > 1).mapPartitions {
        dataprep.process_records_in_partition(_)
      }

    mapop.map(x=> {
      s"${x._1},${x._2.trim},${x._3},${x._4}"
    }).saveAsTextFile(c_output_file_path)

//    val mapopcnt = mapop.filter(x => {
//      x._1 == "URL"
//    }).count()
//    LOGR.info(s"Total mapopcnt record count : {}",mapopcnt)
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
