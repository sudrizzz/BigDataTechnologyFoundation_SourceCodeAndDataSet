package bigdata.ch03;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
public class HBaseRead{
    public static void main(String[] args) throws IOException{
        Configuration conf=HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","zk1,zk2");
        Connection connection=ConnectionFactory.createConnection(conf);
        String tableName="test-hbase";
        String columnName="info";
        String rowkey="rk1";
        String qulifier="c1";
        Table table=connection.getTable(TableName.valueOf(tableName));
        Get get=new Get(rowkey.getBytes());
        get.addColumn(columnName.getBytes(),qulifier.getBytes());
        Result result=table.get(get);
        String valueStr=Bytes.toString(result.getValue(columnName.getBytes(),
                                                  qulifier.getBytes()));
				System.out.println(valueStr);
				table.close();
				connection.close();
        }
}
