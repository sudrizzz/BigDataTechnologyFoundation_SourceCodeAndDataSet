package bigdata.ch03;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
public class HBaseCreate{
    public static void main(String[] args) throws IOException{
       Configuration conf=HBaseConfiguration.create();
       conf.set("hbase.zookeeper.quorum","zk1,zk2");
       Connection connection=ConnectionFactory.createConnection(conf);
       String tableName="test-hbase";
       String columnName="info";
       Admin admin=connection.getAdmin();
       HTableDescriptor tableDescriptor=new 
                        HTableDescriptor(TableName.valueOf(tableName));
      admin.createTable(tableDescriptor);
      HColumnDescriptor columnDescriptor=new HColumnDescriptor(columnName);
      admin.addColumn(TableName.valueOf(tableName),columnDescriptor);
      admin.close();
      connection.close();
   }
}


