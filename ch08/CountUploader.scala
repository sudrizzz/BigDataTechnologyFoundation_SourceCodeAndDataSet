import org.apache.Spark.sql.SparkSession
import org.apache.Spark.{SparkConf, SparkContext}

object CountUploader {
  val pattern = """(\S+)\s+(\S+)\s+(\d+)\s+(\D+[a-zA-Z])\s+(\d+)\s+(\d+)\s+(\d+\.?\d*)\s+(\d+)\s+(\d+)\s+(.*)""".r

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("CountUploader")
    val sc = new SparkContext(conf)

    val raw = sc.textFile(args(0)) 
    val filtered = raw.filter( pattern.findFirstIn(_).isDefined )
    val ones = filtered.map {
      case pattern(videoID, uploader, age, category, length, views, rate, ratings, comments, relatedIDs) => {
        (uploader, 1)
      }
    }
    val counts = ones.reduceByKey(_ + _)
    counts.colloct().foreach( println )
  }
}
