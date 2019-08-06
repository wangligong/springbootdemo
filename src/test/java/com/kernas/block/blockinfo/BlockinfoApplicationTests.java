package com.kernas.block.blockinfo;

import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.Transaction;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BlockinfoApplicationTests {

//	@Autowired
	private Web3j web3j;

	public static void main(String[] args) {
		String str = "123";
		str = "2348" + "123123";
		System.out.println(str);
	}

	@Test
	public void test12(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		String dateStr = sdf.format(date);

		String dateStart = "2018-09-20 00:00:00";
		String dateEnd = "2018-09-22 00:00:00";


		Date now1 = str2Date(dateStr);
		Date dateStart1 = str2Date(dateStart);
		Date dateEnd1 = str2Date(dateEnd);


		if(now1.getTime() > dateStart1.getTime() && now1.getTime() < dateEnd1.getTime()) {
			System.out.println("符合规则" + now1);
		}






 	}

//	public static Date str2Date(String date) {
//
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			return  format.parse(date);
//		}catch (Exception e) {
////			logger.error("str2Date error");
//		}
//		return null;
//	}
	public static Date str2Date(String date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		try {
			return  format.parse(date);
		}catch (Exception e) {
	//			logger.error("str2Date error");
		}
		return null;
	}


	@Test
	public void countInfo() {
			List<String> list = new ArrayList<String>();
//        list.add("C:/addrFcoin/addrF.txt");
//        list.add("C:/addrFcoin/addrFlood.txt");
//        list.add("C:/addrFcoin/addrNew.txt");



		list.add("C:/Users/PC/Desktop/archives/counts.log.2018-10-15");
		list.add("C:/Users/PC/Desktop/63/counts.log.2018-10-15");

//		list.add("C:\\Users\\PC\\Desktop\\archives\\counts.log.2018-09-22");
//		list.add("C:\\Users\\PC\\Desktop\\63\\counts.log.2018-09-22");
//		list.add("C:\\Users\\PC\\Desktop\\archives\\counts.log.2018-09-23");
//		list.add("C:\\Users\\PC\\Desktop\\63\\counts.log.2018-09-23");
//		list.add("C:\\Users\\PC\\Desktop\\archives\\counts.log.2018-09-24");
//		list.add("C:\\Users\\PC\\Desktop\\63\\counts.log.2018-09-24");
//		list.add("C:/addrFcoin/FtAccountAddr.txt")

			Set<String> addrSet  = new HashSet<String>();
			Iterator<String> itList = list.iterator();

			Integer count = 0;
			while(itList.hasNext()) {
				String fileName = itList.next();
				System.out.println(fileName);
				File file = new File(fileName);
				BufferedReader reader = null;
				try {
					System.out.println("以行为单位读取文件内容，一次读一整行：");
					reader = new BufferedReader(new FileReader(file));
					String tempString = null;
//                int line = 1;
					// 一次读入一行，直到读入null为文件结束
					while ((tempString = reader.readLine()) != null && tempString != "") {

						count ++;

						// 显示行号
//						System.out.println("tempString:" + tempString);
//						String [] strings = tempString.split("RouletteServiceImpl");

//						String str = strings[1];

//						System.out.println(str);

//						boolean b = str.contains("|H5|");
////						System.out.println(b);
//						if(b) {
//							h5LoginCount ++;
//							if(str.contains("|1|")){
//								h5LogingNew ++;
//							}else {
//							}
//
//						} else {
//							bigooLoginCount ++;
//							if(str.contains("|1|")) {
//								bigooLoginNew ++;
//							}
//						}
					}
					reader.close();



				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e1) {
						}
					}
				}
			}
			System.out.println("币谷登录抽奖：" + count );
			try {
//				writeAddrFileBySet(addrSet);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("write finsh");
	}









	@Test
	public void test() {
		Set<String> set = new HashSet<String>();
		System.out.print("-----------------------------------------------------------------");
		File file = new File("C:/Users/PC/Desktop/ethAddr/filterAddr.txt");
		List<String> list = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null && tempString != "") {
				set.add(tempString);
				line++;
				System.out.println("----------------------------------" + line);
			}
			reader.close();

			System.out.println("---------" + set.size());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	@Test
	public void contextLoads() {
		Set<String> set1 = new HashSet<String>();
		set1.add("123456");
		set1.add("wangligong");
		set1.add("123456");

		System.out.print(set1);

	}


	@Test
    public void genEmail() {
	    Set<String> set = new HashSet<>();

	    Random rand = new Random();

	    for(int j = 0; j < 10000; j++) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < 8 ; i++) {
                int randInt = rand.nextInt(9) + 1;
                sb.append(randInt);
            }
            sb.append("@qq.com");
            set.add(sb.toString());
        }
        try {
            writeFileBySet(set);
        }catch (Exception e) {

        }

//        System.out.println(set);

//        System.out.println(sb.toString());

//	    int i = rand.nextInt(10);
//
//	    System.out.println(i);



    }


    public void writeFileBySet(Set<String> set) throws IOException {
//        File fout = new File("/home/eth/addr" + (startBlockNumber + 1) +"_"+endBlockNumber +".txt");
        File fout = new File("C:/eth/email.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()) {
            String next = iterator.next();
//            System.out.println(next);
            bw.write(next);
            bw.newLine();
        }
        bw.close();
    }

}
