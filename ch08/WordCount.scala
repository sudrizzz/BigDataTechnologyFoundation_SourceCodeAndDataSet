import org.apache.Spark.SparkConf
import org.apache.Spark.SparkContext

class WordCount {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("CountUploaders")
    val sc = new SparkContext(conf)
   	
     val lines = Spark.read.textFile(args(0))
     val words = lines.flatMap(line => line.split(" "))
     val pairs = words.map( word => (word , 1) )
     val wordCounts = pairs.reduceByKey{ _ + _ }
 wordCounts.foreach(
   word => println(word._1 + " " + word._2))   }
