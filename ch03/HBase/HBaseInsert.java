package bigdata.ch03;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
public class HBaseInsert{
    public static void main(String[] args) throws IOException{
        Configuration conf=HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","zk1,zk2");
        Connection connection=ConnectionFactory.createConnection(conf);
        String tableName="test-hbase";
        String columnName="info";
        String rowkey="rk1";
        String qulifier="c1";
        String value="value1";
        Table table=connection.getTable(TableName.valueOf(tableName));
        Put put=new Put(rowkey.getBytes());
put.addColumn(columnName.getBytes(),qulifier.getBytes(),value.getBytes());
        table.put(put);
        table.close();
        connection.close();
     }
}
