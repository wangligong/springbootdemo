package com.kernas.block.blockinfo;

import com.kernas.block.blockinfo.service.ScanBlock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BlockinfoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BlockinfoApplication.class, args);
		ScanBlock scanBlock = (ScanBlock) context.getBean("scanBlock");
//		scanBlock.test1();
//		scanBlock.scanBlock();
//		MutilScanBlock mutilScanBlock = (MutilScanBlock) context.getBean("mutilScanBlock");
//		mutilScanBlock.sacnBlock();
		scanBlock.readAddrToFilter();
	}
}
