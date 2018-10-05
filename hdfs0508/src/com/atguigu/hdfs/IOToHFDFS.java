package com.atguigu.hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

public class IOToHFDFS {

	// 文件的上传
	@SuppressWarnings("resource")
	@Test
	public void putFileToHDFS() throws IOException, InterruptedException, URISyntaxException{
		
		// 1 获取文件系统
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
		
		// 2 获取输出流
		FSDataOutputStream fos = fs.create(new Path("/user/atguigu/output/dongsi.txt"));
		
		// 3 获取输入流
		FileInputStream fis = new FileInputStream(new File("e:/dongsi.txt"));
		
		try {
			// 4 流对接
			IOUtils.copyBytes(fis, fos, configuration);	
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			// 5 关闭资源
			IOUtils.closeStream(fis);
			IOUtils.closeStream(fos);
		}
	}
	
	// 下载文件
	@SuppressWarnings("resource")
	@Test
	public void getFileFromHDFS() throws IOException, InterruptedException, URISyntaxException{
		// 1 获取文件系统
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
		
		// 2 获取输入流
		FSDataInputStream fis = fs.open(new Path("/user/atguigu/xiyou.txt"));
		
		// 3 创建输出流
		FileOutputStream fos = new FileOutputStream(new File("e:/xiyou.txt"));
		
		try {
			// 4 流的对接
			IOUtils.copyBytes(fis, fos, configuration);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			// 5 关闭资源
			IOUtils.closeStream(fis);
			IOUtils.closeStream(fos);
		}
	}
	
	// 下载大文件的第一块数据
	@SuppressWarnings("resource")
	@Test
	public void getFileFromHDFSSeek1() throws IOException, InterruptedException, URISyntaxException{
		// 1 获取文件系统
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
		
		// 2 获取输入流
		FSDataInputStream fis = fs.open(new Path("/user/atguigu/input/hadoop-2.7.2.tar.gz"));
		
		// 3 创建输出流
		FileOutputStream fos = new FileOutputStream(new File("e:/hadoop-2.7.2.tar.gz.part1"));
		
		// 4 流对接（只读取128m）
		byte[] buf = new byte[1024];
		// 1024 * 1024 * 128
		
		for(int i = 0; i < 1024 * 128;i++){
			fis.read(buf);
			fos.write(buf);
		}
		
		try {
			// 5 关闭资源
			IOUtils.closeStream(fis);
			IOUtils.closeStream(fos);
		} catch (Exception e) {
		}
	}
	
	// 下载大文件的第二块数据
	@SuppressWarnings("resource")
	@Test
	public void getFileFromHDFSSeek2() throws IOException, InterruptedException, URISyntaxException{
		// 1 获取文件系统
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
		
		// 2 获取输入流
		FSDataInputStream fis = fs.open(new Path("/user/atguigu/input/hadoop-2.7.2.tar.gz"));
		
		// 3 创建输出流
		FileOutputStream fos = new FileOutputStream(new File("e:/hadoop-2.7.2.tar.gz.part2"));
		
		// 4 流对接（指向第二块数据的首地址）
		// 定位到128m
		fis.seek(1024*1024*128);
		
		try {
			IOUtils.copyBytes(fis, fos, configuration);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			// 5 关闭资源
			IOUtils.closeStream(fis);
			IOUtils.closeStream(fos);
		}
	}
	
}
