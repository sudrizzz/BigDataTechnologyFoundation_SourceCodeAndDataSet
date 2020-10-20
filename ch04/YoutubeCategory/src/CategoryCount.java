package bigdata.ch04;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
public class CategoryCount {
	 public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
	        private final static IntWritable one = new IntWritable(1);

	        //构造文本类Text对象、IntWritable 对象，也可以直接以匿名函数的方式创建
	        private Text tx = new Text();

	        //map 的逻辑，使用tab“\ t”分隔符来分割行，并将值存储在String Array中，以使一行中的所有列都存储在字符串数组中
	        public void map(LongWritable key, Text value, Context context)
	            throws IOException, InterruptedException {
	            //拿到每一行数据
	            String line = value.toString();
	            String[] str = line.split("\t");

	            //过滤字段
	            if (str.length > 5) {
	                tx.set(str[3]);
	            }

	            //输出key,value
	            context.write(tx, one);
	        }
	    }

	    //编写 reduce，接收 map 阶段传来的 kv 键值对，输出的类型和传进来的类型一致
	    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
	        //reduce????
	        public void reduce(Text key, Iterable<IntWritable> values,
	            Context context) throws IOException, InterruptedException {
	            int sum = 0;

	            //累加求和评分
	            for (IntWritable v : values) {
	                sum += v.get();
	            }

	            //写出去
	            context.write(key, new IntWritable(sum));
	        }
	    }
	  //主函数
	    public static void main(String[] args) throws Exception {
	        Configuration conf = new Configuration();
	        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();  
	        if(otherArgs.length < 2) {  
	            System.err.println("Usage: wordcount <in> [<in>...] <out>");  
	            System.exit(2);  
	        } 
	        @SuppressWarnings("deprecation")
	        Job job = new Job(conf, "categories");
	        //设置生产 jar 包所使用的类
	        job.setJarByClass(CategoryCount.class);
	        //设置 Map 类的输入输出类型
	        job.setMapOutputKeyClass(Text.class);
	        job.setMapOutputValueClass(IntWritable.class);
	        //设置 Reduce 类的输入输出类型    
	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(IntWritable.class);
	        //设置 Map, Reduce 类的 class 参数
	        job.setMapperClass(Map.class);
	        job.setReducerClass(Reduce.class);
	        //指定格式化用的类型
	        job.setInputFormatClass(TextInputFormat.class);
	        job.setOutputFormatClass(TextOutputFormat.class);
	        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	    }
}
