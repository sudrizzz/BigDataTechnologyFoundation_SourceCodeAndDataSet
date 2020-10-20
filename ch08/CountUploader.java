import org.apache.Spark.SparkConf;
import org.apache.Spark.api.java.JavaSparkContext;
import org.apache.Spark.api.java.JavaPairRDD;
import org.apache.Spark.api.java.JavaRDD;
import scala.Tuple2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CountUploader {
  // 用来提取匹配和提取信息的正则表达式
  private static final Pattern EXTRACT = Pattern.compile("(\\S+)\\s+(\\S+)\\s+(\\d+)\\s+(\\D+[a-zA-Z])\\s+(\\d+)\\s+(\\d+)\\s+(\\d+\\.?\\d*)\\s+(\\d+)\\s+(\\d+)\\s+(.*)");

  public static void main(String[] args) throws Exception {
    SparkConf conf = new SparkConf().setAppName("CountUploader");
    JavaSparkContext sc = new JavaSparkContext(conf);
	// 默认按行读取数据
JavaRDD<String> lines = sc.textFile(args[0]);
// 实现过滤出无效数据：即匹配不成功的数据
JavaRDD<String> filtered = lines.filter(
s -> EXTRACT.matcher(s).matches());
	// 使用map操作，对每条记录进行匹配，提取出用户ID信息，并组成键值对
    JavaPairRDD<String, Integer> ones = filtered.mapToPair((String s) -> {
      Matcher m = EXTRACT.matcher(s);
      boolean result = m.matches();
      return new Tuple2<>(m.group(2), 1);
});
// 和WordCount一样按用户ID进行累加求和
    JavaPairRDD<String, Integer> counts = ones.reduceByKey((a, b) -> a + b);
	// 打印结果
    for (Tuple2<?, ?> t : counts.collect()) {
      System.out.println(t._1() + " -> " + t._2());
}
sc.stop();
  }
}
