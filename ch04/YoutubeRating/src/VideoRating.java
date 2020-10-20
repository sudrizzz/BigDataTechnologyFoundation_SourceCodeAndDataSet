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
import java.io.IOException;
import org.apache.hadoop.util.GenericOptionsParser;

public class VideoRating {
	  //Map类，封装KV键值对 <k1,v1,k2,v2> ， k1 是偏移量，v1 是这行数据，输出类型是这行文本 k2 ，浮点类型 v2
    public static class Map extends Mapper<LongWritable, Text, Text, FloatWritable> {
        //创建Text，FloatWritable 对象
        private Text video_name = new Text();
        private FloatWritable rating = new FloatWritable();

        public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
            String line = value.toString();
            String[] str = line.split("\t");

            //使用正则表达式匹配字符串只应包含浮点数，过滤掉小于 7 的无效字段
            if (str.length > 7) {
                video_name.set(str[0]);

                if (str[6].matches("\\d+.+")) {
                    //强制类型转换                    
                    float f = Float.parseFloat(str[6]);
                    rating.set(f);
                }
            }

            context.write(video_name, rating);
        }
    }

    public static class Reduce extends Reducer<Text, FloatWritable, Text, FloatWritable> {
        public void reduce(Text key, Iterable<FloatWritable> values,
            Context context) throws IOException, InterruptedException {
            float sum = 0;
            int l = 0;

            for (FloatWritable val : values) {
                l += 1;
                //累加value
                sum += val.get();
            }

            sum = sum / l;
            //取平均值
            context.write(key, new FloatWritable(sum));
        }
    }
    
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();  
        if(otherArgs.length < 2) {  
            System.err.println("Usage: wordcount <in> [<in>...] <out>");  
            System.exit(2);  
        }  
        @SuppressWarnings("deprecation")
        Job job = new Job(conf, "videorating");
        job.setJarByClass(VideoRating.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FloatWritable.class);
        //设置 map ，reduce 类及 class
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        //设置输入输出路径，这里指定的输路径是 HDFS，输出路径是 Linux 的本地文件系统
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
