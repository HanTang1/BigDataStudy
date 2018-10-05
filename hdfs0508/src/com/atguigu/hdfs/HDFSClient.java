package com.atguigu.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.Test;

public class HDFSClient {

	public static void main(String[] args) throws Exception {
		// 0 ????????????
		Configuration configuration = new Configuration();
		// configuration.set("fs.defaultFS", "hdfs://hadoop102:8020");

		// 1 ????????????
		// FileSystem fs = FileSystem.get(configuration);
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

		// 2 ??¡À?¡À?????????????
		fs.copyFromLocalFile(new Path("e:/xiyou1.txt"), new Path("/user/atguigu/xiyou1.txt"));

		// 3 ??¡À?fs
		fs.close();
	}

	// ????????????
	@Test
	public void getFileSystem() throws Exception {
		// 0 ???¡§???????????¨®
		Configuration configuration = new Configuration();

		// 1 ????????????
		// FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"),
		// configuration, "atguigu");
		FileSystem fs = FileSystem.get(configuration);

		// 2?¨°??????????
		System.out.println(fs.toString());

		// 3 ??¡À?¡Á???
		fs.close();
	}

	// ????????
	@Test
	public void putFileToHDFS() throws IOException, InterruptedException, URISyntaxException {
		Configuration configuration = new Configuration();

		// 1 ???????????? ???????????????????????¨²??>?¨´????????????????>????????????????
		// FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"),
		// configuration, "atguigu");
		FileSystem fs = FileSystem.get(configuration);

		// 2 ?????????????¨¹??
		fs.copyFromLocalFile(true, new Path("e:/zyq.txt"), new Path("/user/atguigu/"));

		// 3 ??¡À?¡Á???
		fs.close();
	}

	// ????????
	@Test
	public void getFileFromHDFS() throws Exception {

		// 1 ????????????
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

		// 2 ?????????????¨¹??
		// fs.copyToLocalFile(new Path("/user/atguigu/xiyou.txt"), new
		// Path("e:/xiyou.txt"));
		fs.copyToLocalFile(false, new Path("/user/atguigu/xiyou.txt"), new Path("e:/xiyou.txt"), true);

		// 3 ???¡Â
		fs.close();
	}

	// ???¡§????
	@Test
	public void mkdirAtHDFS() throws Exception {
		// 1 ????????????
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), conf, "atguigu");

		// 2 ???????¡§????????¡Á¡Â
		// fs.mkdirs(new Path("/user/atguigu/xiyou/sunhouzi/houzaizi"));
//		FsPermission p = new FsPermission((short) 777);
		FsPermission p = new FsPermission("677");

		fs.mkdirs(new Path("/user/atguigu/hh/"), p);

		// 3 ??¡À?¡Á???
		fs.close();
	}

	// ??????????
	@Test
	public void deleteAtHDFS() throws IOException, InterruptedException, URISyntaxException {
		// 1 ????????????
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), conf, "atguigu");

		// 2 ??????????¡Á¡Â
		fs.delete(new Path("/user/atguigu/xiyou1.txt"), true);

		// 3 ??¡À?¡Á???
		fs.close();
	}

	// ?¨¹??????????
	@Test
	public void renameAtHDFS() throws IOException, InterruptedException, URISyntaxException {

		// 1 ????????????
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), conf, "atguigu");

		// 2 ?????¨¹????????¡Á¡Â
		fs.rename(new Path("/user/atguigu/zyq.txt"), new Path("/user/atguigu/womenjiehunba.txt"));

		// 3 ??¡À?¡Á???
		fs.close();
	}

	// ?¨¦???????¨º?¨¦
	@Test
	public void readFileAtHDFS() throws IOException, InterruptedException, URISyntaxException {
		// 1 ????????????
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), conf, "atguigu");

		// 2 ?????¨¦???????¨º?¨¦??¡Á¡Â
		RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

		while (listFiles.hasNext()) {
			LocatedFileStatus status = listFiles.next();

			// ????????
			System.out.println(status.getPath().getName());

			// ?¨¦???¨®??
			System.out.println(status.getBlockSize());

			// ???????????¡è??
			System.out.println(status.getLen());

			// ?????¡§??
			System.out.println(status.getPermission());

			// ?????¨¦??????????
			BlockLocation[] blockLocations = status.getBlockLocations();

			for (BlockLocation block : blockLocations) {
				System.out.println(block.getOffset());

				String[] hosts = block.getHosts();

				for (String host : hosts) {
					System.out.println(host);
				}
			}

			System.out.println("-------------------");
		}

		// 3 ??¡À?¡Á???
		fs.close();
	}

	// ????????????????????
	@Test
	public void readfolderAtHDFS() throws IOException, InterruptedException, URISyntaxException {
		// 1 ????????????
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), conf, "atguigu");

		// 2 ????????????????????
		FileStatus[] listStatus = fs.listStatus(new Path("/user/atguigu/"));

		for (FileStatus status : listStatus) {

			if (status.isFile()) {
				System.out.println("f---" + status.getPath().getName());
			} else {
				System.out.println("d--" + status.getPath().getName());
			}
		}

		// 3 ??¡À?¡Á???
		fs.close();
	}

}
