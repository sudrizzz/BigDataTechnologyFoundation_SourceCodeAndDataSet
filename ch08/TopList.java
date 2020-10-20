import org.apache.Spark.SparkConf;
import org.apache.Spark.api.java.JavaSparkContext;
import org.apache.Spark.api.java.JavaPairRDD;
import org.apache.Spark.api.java.JavaRDD;
import scala.Tuple2;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopList {
  private static final Pattern EXTRACT = Pattern.compile("(\\S+)\\s+(\\S+)\\s+(\\d+)\\s+(\\D+[a-zA-Z])\\s+(\\d+)\\s+(\\d+)\\s+(\\d+\\.?\\d*)\\s+(\\d+)\\s+(\\d+)\\s+(.*)");

  public static void main(String[] args) throws Exception {
    SparkConf conf = new SparkConf().setAppName("CountUploader");
    JavaSparkContext sc = new JavaSparkContext(conf);

    JavaRDD<String> lines = sc.textFile(args[0]);
JavaRDD<String> filtered = lines.filter(
  s -> EXTRACT.matcher(s).matches());
	// 这里需要提取用户ID和视频ID
JavaPairRDD<String, String> records = filtered.mapToPair(s -> {
      Matcher m = EXTRACT.matcher(s);
      boolean result = m.matches();
      return new Tuple2<>(m.group(2), m.group(1));
    });
	// 将<用户ID,视频ID>按用户ID分组，并将结果转换成集合List<String>
    JavaPairRDD<String, List<String>> groups =
        records.groupByKey().mapToPair(t -> {
          List<String> list = new ArrayList<>();
          t._2().forEach(list::add);
          return new Tuple2<>(t._1(), list);
        });
	// 由于Java版本没有sortBy操作，需要自己实现类似逻辑
    JavaRDD<Tuple2<String, List<String>>> tops =
        groups.keyBy(t -> t._2().size()).sortByKey(false).values();
    List<Tuple2<String, List<String>>> topList = tops.take(100);
	// 打印结果
    for (Tuple2<String, List<String>> t : topList) {
System.out.println("User: " + t._1() + 
  ", Number of videos: " + t._2().size());
      for (String s : t._2()) {
        System.out.println(s);
      }
    }
    sc.stop();
  }
}
