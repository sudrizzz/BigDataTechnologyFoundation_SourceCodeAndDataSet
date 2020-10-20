package bigdata.ch04;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BooleanWritable.Comparator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TopListProcess {
	public static class TopListProcessMapper extends Mapper<LongWritable, Text, Text, Text> {
		// 创建Text，FloatWritable 对象
		private Text videoID = new Text();
		private Text uploaderName = new Text();

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			String[] str = line.split("\t");
			if (str.length > 7) {
				videoID.set(str[0]);
				uploaderName.set(str[1]);
			}
			context.write(uploaderName, videoID);
		}
	}

	public static class TopListProcessReduce extends Reducer<Text, Text, Text, NullWritable> {
		public void reduce(Text uploaderName, Iterable<Text> videoIDs, Context context)throws IOException, InterruptedException {
			int videoNum = 0;
			StringBuffer uploaderName_videoIDs = new StringBuffer();
			TopListBean topListBean = new TopListBean();
			Text key = new Text();
			NullWritable value = NullWritable.get();
			for (Text videoID : videoIDs) {
				uploaderName_videoIDs.append(videoID).append(",");// 用户上传的视频列表
				videoNum += 1; // 用户上传的视频数量
			}
			topListBean.setUploaderName(uploaderName);
			topListBean.setUploadNum(new IntWritable(videoNum));
			topListBean.setVideoID(new Text(uploaderName_videoIDs.substring(0, uploaderName_videoIDs.length() - 1)));// 去除视频列表字符串末尾多出的','符号
			key.set(topListBean.toString());
			context.write(key, value);

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
		Job job = new Job(conf, "TopList");
		job.setJarByClass(TopListProcess.class);
		job.setMapperClass(TopListProcessMapper.class);
		job.setReducerClass(TopListProcessReduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		// 设置输入输出路径，这里指定的输路径是 HDFS，输出路径是 Linux 的本地文件系
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
