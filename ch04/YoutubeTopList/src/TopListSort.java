package bigdata.ch04;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.BooleanWritable.Comparator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TopListSort {
	public static class TopListSortMapper extends Mapper<LongWritable, Text, IntWritable, Text> {
		private IntWritable uploaderNum = new IntWritable();
		// TopListBean topListBean = new TopListBean();
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			String[] str = line.split("#");
			uploaderNum.set(Integer.valueOf(str[1]));// 获取上传视频数量，作为mapper函数的key输出
			context.write(uploaderNum, new Text(line.toString()));
		}
	}

	public static class TopListSortReduce extends Reducer<IntWritable, Text, Text, NullWritable> {
		public void reduce(IntWritable uploaderNum, Iterable<Text> lines, Context context)
				throws IOException, InterruptedException {
			for (Text line : lines) {
				context.write(new Text(line.toString()), NullWritable.get());
			}
		}
	}

	public static class myComparator extends Comparator {
		@SuppressWarnings("rawtypes")
		public int compare(WritableComparable a, WritableComparable b) {
			return -super.compare(a, b);
		}

		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
			return -super.compare(b1, s1, l1, b2, s2, l2);
		}

	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
		if (otherArgs.length < 2) {
			System.err.println("Usage: wordcount <in> [<in>...] <out>");
			System.exit(2);
		}
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "TopListSort");
		job.setJarByClass(TopListSort.class);
		job.setMapperClass(TopListSortMapper.class);
		job.setReducerClass(TopListSortReduce.class);

		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		job.setSortComparatorClass(myComparator.class);
		// 设置输入输出路径，这里指定的输路径是 HDFS，输出路径是 Linux 的本地文件系
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
