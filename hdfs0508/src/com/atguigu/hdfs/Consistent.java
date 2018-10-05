package com.atguigu.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Consistent {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		// 1 获取文件系统
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
		
		// 2 获取输出流
		FSDataOutputStream fos = fs.create(new Path("/zaiyiqi"));
		
		// 3 具体的写上传数据
		fos.write("hello world11111".getBytes());
		
		fos.hflush();
		
		// 4 关闭资源
		fos.close();
	}

}
