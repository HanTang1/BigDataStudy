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
		// 1 ��ȡ�ļ�ϵͳ
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
		
		// 2 ��ȡ�����
		FSDataOutputStream fos = fs.create(new Path("/zaiyiqi"));
		
		// 3 �����д�ϴ�����
		fos.write("hello world11111".getBytes());
		
		fos.hflush();
		
		// 4 �ر���Դ
		fos.close();
	}

}
