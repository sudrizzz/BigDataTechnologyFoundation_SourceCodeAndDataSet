import org.apache.Spark.sql.SparkSession
import org.apache.Spark.{SparkConf, SparkContext}

object TopList {
  val pattern = """(\S+)\s+(\S+)\s+(\d+)\s+(\D+[a-zA-Z])\s+(\d+)\s+(\d+)\s+(\d+\.?\d*)\s+(\d+)\s+(\d+)\s+(.*)""".r

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("TopList")
    val sc = new SparkContext(conf)

    val raw = sc.textFile(args(0)) 
    val filtered = raw.filter( pattern.findFirstIn(_).isDefined )
    val records = filtered.map {
      case pattern(videoID, uploader, age, category, length, views, rate, ratings, comments, relatedIDs) => {
        (uploader, videoID)
      }
    }

    val groups = records.groupByKey().map {
      case (u, g) => (u, g.toSeq)
    }
    val tops = groups.sortBy(_._2.length, false)
    val topList = tops.take(100)

    topList.foreach {
      case (u, g) => {
        println("User: " + u + ", Number of Videos: " + g.length)
        g.foreach(println)
      }
}
sc.stop()
  }
}
