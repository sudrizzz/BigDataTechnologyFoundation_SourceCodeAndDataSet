package bigdata.ch03;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Admin;
public class HBaseDelete {
   public static void main(String[] args) throws IOException{
    	Configuration conf=HBaseConfiguration.create();
   	conf.set("hbase.zookeeper.quorum","zk1,zk2");
   	Connection connection=ConnectionFactory.createConnection(conf);
  	String tableName="test-hbase";
  	Admin admin=connection.getAdmin();
      admin.disableTable(TableName.valueOf(tableName));
  	admin.deleteTable(TableName.valueOf(tableName));
 		admin.close();
 		connection.close();
 	 }
}
