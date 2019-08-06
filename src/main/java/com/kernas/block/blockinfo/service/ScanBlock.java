package com.kernas.block.blockinfo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.Transaction;

import java.io.*;
import java.net.ConnectException;
import java.util.*;

/**
 * Created by wlg on 2017/12/26.
 */
@Service
public class ScanBlock {

    private final static Logger logger = LoggerFactory.getLogger(ScanBlock.class);

    @Value("${startBlockNumber}")
    private int startBlockNumber;
    @Value("${endBlockNumber}")
    private int endBlockNumber;

    @Autowired
    private Web3j web3j;

    public void readAddrToFilter() {
        List<String> list = new ArrayList<String>();
//        list.add("C:/addrFcoin/addrF.txt");
//        list.add("C:/addrFcoin/addrFlood.txt");
//        list.add("C:/addrFcoin/addrNew.txt");
        list.add("C:/addrFcoin/drcTokenAddr.txt");
        list.add("C:/addrFcoin/FtAccountAddr.txt");
//        list.add("C:/eth/addr4270001_4280000.txt");
//
//
//        list.add("C:/eth/addr4280001_4290000.txt");
//        list.add("C:/eth/addr4290001_4300000.txt");
//        list.add("C:/eth/addr4300001_4500000.txt");
//        list.add("C:/eth/addr4500001_4510000.txt");
//
//        list.add("C:/eth/addr4510001_4520000.txt");
//        list.add("C:/eth/addr4520001_4530000.txt");
//        list.add("C:/eth/addr4530001_4540000.txt");
//        list.add("C:/eth/addr4540001_4550000.txt");

//        list.add("C:/eth/addr4550001_4560000.txt");
//        list.add("C:/eth/addr4560001_4570000.txt");
//        list.add("C:/eth/addr4570001_4580000.txt");
//        list.add("C:/eth/addr4580001_4590000.txt");
//
//        list.add("C:/eth/addr4590001_4600000.txt");
//        list.add("C:/eth/addr4600001_4610000.txt");
//        list.add("C:/eth/addr4610001_4620000.txt");
//        list.add("C:/eth/addr4620001_4630000.txt");
//
//        list.add("C:/eth/addr4630001_4640000.txt");
//        list.add("C:/eth/addr4640001_4650000.txt");
//        list.add("C:/eth/addr4650001_4660000.txt");
//        list.add("C:/eth/addr4660001_4670000.txt");
//
//        list.add("C:/eth/addr4670001_4680000.txt");
//        list.add("C:/eth/addr4680001_4690000.txt");
//        list.add("C:/eth/addr4690001_4695000.txt");
//        list.add("C:/eth/addr4695001_4700000.txt");
//
//        list.add("C:/eth/addr4700001_4705000.txt");
//        list.add("C:/eth/addr4705001_4710000.txt");
//        list.add("C:/eth/addr4710001_4715000.txt");
//        list.add("C:/eth/addr4715001_4720000.txt");
//
//        list.add("C:/eth/addr4720001_4770000.txt");
//        list.add("C:/eth/addr4770001_4780000.txt");
//        list.add("C:/eth/addr4780001_4790000.txt");
//        list.add("C:/eth/addr4790001_4800000.txt");
//
//        list.add("C:/eth/addr4800001_4850000.txt");
//        list.add("C:/eth/addr4850001_4900000.txt");
//        list.add("C:/eth/addr4900001_4910000.txt");
//        list.add("C:/eth/addr4910001_4920000.txt");
//
//        list.add("C:/eth/addr4920001_4970000.txt");
//        list.add("C:/eth/addr4970001_4980000.txt");
//        list.add("C:/eth/addr4980001_4990000.txt");
//        list.add("C:/eth/addr4990001_5000000.txt");


        Set<String> addrSet  = new HashSet<String>();
        Iterator<String> itList = list.iterator();

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
                    // 显示行号
//                Request<?, EthGetCode> ethGetCodeRequest = web3j.ethGetCode(tempString, DefaultBlockParameterName.LATEST);
//                    Request<?, EthGetCode> ethGetCodeRequest = web3j.ethGetCode("0x91136d2250fef45d08d3e34bf9e450a7067456d7", DefaultBlockParameterName.LATEST);
//                    EthGetCode ethGetCode = ethGetCodeRequest.send();
//                    byte[] but = ethGetCode.getCode().getBytes();
//                    if(but.length > 2 ){
//                        System.out.println(but.length);
//                        continue;
//                    }
                    addrSet.add(tempString);
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
        try {
            writeAddrFileBySet(addrSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("write finsh");
    }


    public void scanBlock() {
        Date date = new Date();
        long startTime = date.getTime();
        System.out.println(startTime);
//        Request<?, EthBlockNumber> ethBlockNumberRequest = web3j.ethBlockNumber();
//        int blockNumber = 1 + 500000 + 500000 + 500000;
//        int endBlockNumber = 500000 + 500000 + 500000 + 500000;
        int blockNumber = startBlockNumber + 1;
//        int endBlockNumber = endBlockNumber;
//        try {
//            EthBlockNumber ethBlockNumber = ethBlockNumberRequest.send();
//            blockNumber = ethBlockNumber.getBlockNumber().intValue();
//            endBlockNumber = blockNumber - 100;
//            System.out.println("开始块/结束块" + blockNumber + "--------------------------" + endBlockNumber);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("开始块/结束块" + blockNumber + "--------------------------" + endBlockNumber);
        Set<String> set = new HashSet<String>();
        String tagStr = "开始块/结束块" + blockNumber + "--------------------------" + endBlockNumber;
        set.add(tagStr);
        int count = 0;
        while (blockNumber <= endBlockNumber) {

            Request<?, EthBlock> ethBlockRequest = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(blockNumber), true);
            EthBlock ethBlock = null;
            try {
                ethBlock = ethBlockRequest.send();
            } catch (Exception e) {
                System.out.println("出现异常--->当前块号是：" + blockNumber );
                //出现异常，将读取好的写入文件
                try {
                    writeFileBySet(set);
                } catch (IOException e1) {
                    System.out.println("写入文件失败！");
                    e.printStackTrace();
                }
                e.printStackTrace();
            }
            EthBlock.Block block = ethBlock.getBlock();

            List<EthBlock.TransactionResult> txList = block.getTransactions();
            Iterator it = txList.iterator();

            while (it.hasNext()) {
                EthBlock.TransactionResult transactionResult = (EthBlock.TransactionResult) it.next();
                Transaction tx = (EthBlock.TransactionObject) transactionResult.get();
                String addr1 = tx.getFrom();
                String addr2 = tx.getTo();

                if(!ifContract(addr1)) {
                    set.add(addr1);
//                    System.out.println(++count);
                }

                if(!ifContract(addr2)) {
                    set.add(addr2);
//                    System.out.println(++count);
                }
//                System.out.println(addr1);
//                System.out.println(addr2);
//                logger.debug("------------------addr1----------------" + addr1);
//                logger.debug("------------------addr2----------------" + addr2);
            }
//            blockNumber --;
            blockNumber++;
            System.out.println("----------------------------------------------------" + blockNumber);
        }
        try{
            writeFileBySet(set);
        }catch (Exception e) {

        }

        Date date2 = new Date();
        long endTime = date2.getTime();
        System.out.println("-----endTime----" + endTime + ",消耗的时间为" + (endTime - startTime));
    }

    public boolean ifContract(String addr) {
        try{
            Request<?, EthGetCode> ethGetCodeRequest = web3j.ethGetCode(addr, DefaultBlockParameterName.LATEST);
            EthGetCode ethGetCode = ethGetCodeRequest.send();
            byte[] but = ethGetCode.getCode().getBytes();
            if(but.length > 2) {
                return true;
            }
        }catch (Exception e) {
            return true;
        }
        return false;
    }

    public void test1() {
        System.out.print("-----------------------------------------------------------------");
        File file = new File("/home/eth/123123123123123123.txt");
        List<String> list = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null && tempString != "") {
                // 显示行号
//                Request<?, EthGetCode> ethGetCodeRequest = web3j.ethGetCode(tempString, DefaultBlockParameterName.LATEST);
                Request<?, EthGetCode> ethGetCodeRequest = web3j.ethGetCode("0x91136d2250fef45d08d3e34bf9e450a7067456d7", DefaultBlockParameterName.LATEST);
                EthGetCode ethGetCode = ethGetCodeRequest.send();
                byte[] but = ethGetCode.getCode().getBytes();
                if(but.length > 2 ){
                    System.out.println(but.length);
                    continue;
                }

                if(!list.contains(tempString)) {
                    list.add(tempString);
                }

                System.out.println("line " + line + ": " + tempString);
                line++;

            }
            System.out.print(list.size());

            reader.close();

            writeFile(list);
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

    public static void writeFile(List<String> list) throws IOException {
        File fout = new File("/home/eth/addr1.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        Iterator<String> iterator = list.iterator();

        int i = 0;
        while(iterator.hasNext()) {

            String next = iterator.next();
//            if(i < 3000) {
                bw.write(next);
                bw.newLine();
//                i++;
//            }
        }
        bw.close();
    }

    public void writeFileBySet(Set<String> set) throws IOException {
//        File fout = new File("/home/eth/addr" + (startBlockNumber + 1) +"_"+endBlockNumber +".txt");
        File fout = new File("C:/eth/addr" + (startBlockNumber + 1) +"_"+endBlockNumber +".txt");
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

    public void writeAddrFileBySet(Set<String> set) throws IOException {
        File fout = new File("C:/addrFcoin/FtAccountAddr2.txt");
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

