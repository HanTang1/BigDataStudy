package com.atguigu.hdfs;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.net.DNSToSwitchMapping;

public class AutoRack implements DNSToSwitchMapping {

	@Override
	public List<String> resolve(List<String> names) {
		ArrayList<String> lists = new ArrayList<String>();
		if (names != null && names.size() > 0) {
			for (String name : names) {
				int ip = 0;
                //1 获取ip地址
				if (name.startsWith("hadoop")) {
					String no = name.substring(6);
					// hadoop102
					ip = Integer.parseInt(no);
				} else if (name.startsWith("192")) {
					// 192.168.10.102
					ip = Integer.parseInt(name.substring(name.lastIndexOf(".") + 1));
				}

                //2 定义机架
				if (ip < 104) {
					lists.add("/rack1/" + name);
				} else {
					lists.add("/rack2/" + name);
				}
			}
		}
		
        // 把ip地址打印出来
		try {
			FileOutputStream fos = new FileOutputStream("/home/atguigu/name.txt");

			for (String name : lists) {
				fos.write((name + "\r\n").getBytes());
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lists;


		// 返回
//		return lists;
	}

	@Override
	public void reloadCachedMappings() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reloadCachedMappings(List<String> arg0) {
		// TODO Auto-generated method stub

	}
}
