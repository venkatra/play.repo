package ca.effacious.learn.gcp.spark.wikilink

import org.slf4j.LoggerFactory

/**
  * Serializable fuctions used during data preparation phases
  */
class Dataprep extends Serializable {

  @transient  lazy val LOGR = LoggerFactory.getLogger(getClass.getName)

  def process_records_in_partition(iteratorof_records :Iterator[String])
  :Iterator[Tuple4[String,String,String,String]] = {
    var current_url = ""
    var child_record_without_parent_count = 0

    val mapped = iteratorof_records.map {
      ln => {

      val seqof_records =  ln match {

          case x:String if(x.startsWith("URL")) =>  {
            current_url = x.replace("URL"," ").replaceAll("URL( )+"," ").trim
            Seq(Tuple4("URL" ,"" ,current_url ,current_url))

          }

          case mx:String if(mx.startsWith("MENTION")) =>  {
            //val mx ="MENTION rotation axis   33718   http://en.wikipedia.org/wiki/Rotation"
            val my = mx.replace("MENTION"," ").replaceAll("[0-9]"," ").trim.replaceAll("( )+",";")
            val url = my.split(";").last
            val records = my.split(";")
              .filter(x => {x.equals(url) == false})
              .filter(x => {x.trim.length > 1})
              .map(Tuple4("MENTION" ,_ ,url.trim ,current_url))
            records.toSeq
          }

          case z:String if(z.startsWith("TOKEN")) =>  {
            val tk = z.replace("TOKEN"," ").replaceAll("[0-9]"," ").trim.replaceAll("( )+",";")
            Seq(Tuple4("TOKEN" ,tk ,"-" ,current_url))
          }

        }

        if (current_url.length <= 1) {
          child_record_without_parent_count = child_record_without_parent_count + 1
        }

        seqof_records
      }
    }

    if(child_record_without_parent_count > 0) {
      LOGR.warn("__NO_ASSOCIATED_PARENT_RECORD__ number of records without parent : " + child_record_without_parent_count)
    }

    mapped.flatMap(_.iterator)
  }


}
