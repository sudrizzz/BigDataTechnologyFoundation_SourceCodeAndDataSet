import scala.Tuple2;
import org.apache.Spark.SparkConf;
import org.apache.Spark.sql.java.JavaSparkContext;
import org.apache.Spark.api.java.JavaPairRDD;
import org.apache.Spark.api.java.JavaRDD;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class JavaWordCount {
private static final Pattern kSpace = Pattern.compile(" ");
public static void main(String[] args) throws Exception {
// 创建配置文件和上下文
SparkConf conf = new SparkConf().setAppName("WordCount");
JavaSparkContext sc = new JavaSparkContext(conf);

// 按行读取文件
JavaRDD<String> lines = sc.textFile(args[0]).javaRDD();
//将行切割成词
JavaRDD<String> words = lines.flatMap(
s -> Arrays.asList(kSpace.split(s)).iterator());
// 将词映射成<word, 1>键值对
JavaPairRDD<String, Integer> ones = words.mapToPair(
s-> new Tuple2<>(s, 1));
// 将相同的词的计数累加起来
JavaPairRDD<String, Integer> counts = ones.reduceByKey((a, b)->a + b);
//收集和打印结果
List<Tuple2<String, Integer>> output = counts.collect();
for (Tuple2<?,?> tuple: output) {
System.out.println(tuple._1() + ":" + tuple._2());
}
sc.stop();
}
}
