package bigdata.ch04;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class TopListBean implements Writable {
	private Text videoID; // 视频ID
	private Text uploaderName; // 上传者用户名
	private IntWritable uploadNum; // 上传视频数量

	public Text getVideoID() {
		return videoID;
	}

	public void setVideoID(Text videoID) {
		this.videoID = videoID;
	}

	public Text getUploaderName() {
		return uploaderName;
	}

	public void setUploaderName(Text uploaderName) {
		this.uploaderName = uploaderName;
	}

	public IntWritable getUploadNum() {
		return uploadNum;
	}

	public void setUploadNum(IntWritable uploadNum) {
		this.uploadNum = uploadNum;
	}
	// 反序列化
	@Override
	public void readFields(DataInput in) throws IOException {
		this.videoID = new Text(in.readUTF());
		this.uploaderName = new Text(in.readUTF());
		this.uploadNum = new IntWritable(in.readInt());

	}
	// 序列化
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(videoID.toString());
		out.writeUTF(uploaderName.toString());
		out.writeInt(uploadNum.get());

	}
	@Override
	public String toString() {
		// return "上传者用户名:" + uploaderName + ",上传数量:" + uploadNum+ ",视频ID列表:[" + videoID
		// + "]";
		return uploaderName + "#" + uploadNum + "#[" + videoID + "]";
	}

}
