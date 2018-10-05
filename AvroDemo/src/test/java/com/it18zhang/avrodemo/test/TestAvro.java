package com.it18zhang.avrodemo.test;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/23.
 */
public class    TestAvro {

//    @Test
//    public void write() throws Exception {
//        //创建writer对象
//        SpecificDatumWriter empDatumWriter = new SpecificDatumWriter<Employee>(Employee.class);
//        //写入文件
//        DataFileWriter<Employee> empFileWriter = new DataFileWriter<Employee>(empDatumWriter);
//
//        //创建对象
//        Employee e1 = new Employee();
//        e1.setName("tomas");
//        e1.setAge(12);
//
//        //串行化数据到磁盘
//        empFileWriter.create(e1.getSchema(), new File("d:/avro/data/e1.avro"));
//        empFileWriter.append(e1);
//        empFileWriter.append(e1);
//        empFileWriter.append(e1);
//        empFileWriter.append(e1);
//        //关闭流
//        empFileWriter.close();
//    }
//
//    @Test

//    public void read() throws Exception {
//        //创建writer对象
//        SpecificDatumReader empDatumReader = new SpecificDatumReader<Employee>(Employee.class);
//        //写入文件
//        DataFileReader<Employee> dataReader = new DataFileReader<Employee>(new File("d:/avro/data/e1.avro")  ,empDatumReader);
//        Iterator<Employee> it = dataReader.iterator();
//        while(it.hasNext()){
//            System.out.println(it.next().getName());
//        }
//    }

    /**
     * 直接使用schema文件进行读写，不需要编译
     */
    @Test
    public void writeInSchema() throws  Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://localhost:8020");
        conf.setBoolean("dfs.support.append", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        conf.setBoolean("dfs.client.block.write.replace-datanode-on-failure.enable", true);
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("hdfs://192.168.0.100:8020/e6.avro") ;


        FSDataOutputStream fout = null;
//        if (!fs.exists(path)) {
//            fout = fs.create(path,false);
//        }else{
//            fout = fs.append(path);
//        }

        fout = fs.create(path);

        Schema schema = new Schema.Parser().parse(new File("/Users/HanTang/Desktop/emp.avsc"));

        //创建GenericRecord,相当于Employee
        GenericRecord e1 = new GenericData.Record(schema);

        e1.put("age", 40);

        //
        DatumWriter w1 = new SpecificDatumWriter (schema);
        DataFileWriter w2 = new DataFileWriter(w1);

        w2.create(schema,fout) ;
        w2.append(e1);
        w2.append(e1);
//        w2.flush();
//        w2.close();
    }

    /**
     * 反串行avro数据
     */
    @Test
    public void readInSchema() throws  Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://192.168.0.100:8020");
        conf.setBoolean("dfs.support.append", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        conf.setBoolean("dfs.client.block.write.replace-datanode-on-failure.enable", true);
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("hdfs://192.168.0.100:8020/e6.avro") ;
        //指定定义的avsc文件。
        Schema schema = new Schema.Parser().parse(new File("/Users/HanTang/Desktop/emp.avsc"));

//        GenericRecord e1 = new GenericData.Record(schema);
//        DatumReader r1 = new SpecificDatumReader (schema);
//        DataFileReader r2 = new DataFileReader(fs.,r1);
//        while(r2.hasNext()){
//            GenericRecord rec = (GenericRecord)r2.next();
//            System.out.println(rec.get("name"));
//        }
//        r2.close();

        FSDataInputStream is = fs.open(path);


        DataFileStream<Object> reader = new DataFileStream<Object>(is,
                new GenericDatumReader<Object>());

        for (Object o : reader) {
                    GenericRecord r = (GenericRecord) o;
                        System.out.println(r.get("age"));
                    }
                IOUtils.cleanup(null, is);
               IOUtils.cleanup(null, reader);
    }

}
