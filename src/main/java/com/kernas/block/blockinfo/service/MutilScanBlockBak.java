package com.kernas.block.blockinfo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wlg on 2018/2/9.
 */
@Service
public class MutilScanBlockBak {

    private final static Logger logger = LoggerFactory.getLogger(ScanBlock.class);

    @Value("${geth.ethHttpUrl}")
    private String ethHttpUrl;

    @Value("${startBlockNumber}")
    private int fileBlockNumber;
    @Value("${startBlockNumber}")
    private volatile int startBlockNumber;
    @Value("${endBlockNumber}")
    private volatile int endBlockNumber;

//    @Autowired
//    private Web3j web3j;

    private volatile Set<String> set = new HashSet<String>();

    private Lock blockNumberLock = new ReentrantLock();
    private Lock setLock = new ReentrantLock();

    public void sacnBlock() {
        Date date = new Date();
        long startTime = date.getTime();
        System.out.println(startTime);

        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        while(true) {
            if (startBlockNumber < endBlockNumber) {
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        task();
                    }
                });
            } else{
                break;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        //大致估算的一个时间-3.6/s---然后开始检测线程任务是否执行完毕
        try {
            Thread.sleep(16000000);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //做30次的检测，若是超过30分钟，都没终止，直接终止。并将数据写入文件
        int i = 1;
        boolean flag = false;
        while(i <= 30) {
            System.out.println("等待执行完毕");
            if(threadPool.isTerminated()) {
                //地址获取完毕，写入文件
                Set<String> set = getSet();
                try {
                    writeFileBySet(set);
                } catch (Exception e) {
                    System.out.println("文件写入失败");
                }
                System.out.println("文件写入成功");
                flag = true;
                break;
            }
            try{
                Thread.sleep(60000);
            }catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }

        if(!flag) {
            threadPool.shutdownNow();
            //地址获取完毕，写入文件
            Set<String> set = getSet();
            try {
                writeFileBySet(set);
            } catch (Exception e) {
                System.out.println("文件写入失败");
            }
            System.out.println("文件写入成功--有线程没有执行完毕");
        }
        Date date2 = new Date();
        long endTime = date2.getTime();
        System.out.println("消耗时间：" + (endTime - startTime));
    }


    public void task() {

        Web3j web3j = Web3j.build(new HttpService(getEthHttpUrl()));

        Set<String> setThread = new HashSet<String>();
        System.out.println("--------------"+startBlockNumber+"-----------------");
        //同步的方法
        int blockNumberThread = updateStartBlcokNumber();

        if(blockNumberThread != -1) {
            for (int i = blockNumberThread; i < blockNumberThread + 600; i++) {
                System.out.println("当前线程" + Thread.currentThread().getName() + "--->" + i);
                Request<?, EthBlock> ethBlockRequest = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), true);
                EthBlock ethBlock = null;
                try {
                    ethBlock = ethBlockRequest.send();
                } catch (Exception e) {
                    System.out.println("出现异常--->当前块号是：" + i);
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

                    if (addr1 != null) {
                        if (!ifContract(addr1, web3j)) {
                            setThread.add(addr1);
                        }
                    }

                    if (addr2 != null) {
                        if (!ifContract(addr2, web3j)) {
                            setThread.add(addr2);
                        }
                    }
                }
            }
        }
        //同步的方法
        updateSet(setThread);
    }

    private int updateStartBlcokNumber() {
        blockNumberLock.lock();
        try{
            if(this.startBlockNumber < endBlockNumber) {
                int blockNumber = this.startBlockNumber;
                this.startBlockNumber = this.startBlockNumber + 600;
//                blockNumberLock.unlock();
                return blockNumber;
            }
            return -1;
        } finally {
            blockNumberLock.unlock();
        }
    }


    private void updateSet(Set<String> setThread) {
        setLock.lock();
        try{
            set.addAll(setThread);
        }finally {
            setLock.unlock();
        }
    }

    public Set<String> getSet() {
        setLock.lock();
        try{
            System.out.println("-----getSet锁-------");
            Set<String> set = this.set;
            return set;
        }finally {
            setLock.unlock();
        }
    }

    public void writeFileBySet(Set<String> set) throws IOException {
//        File fout = new File("/home/eth/addr" + (startBlockNumber + 1) +"_"+endBlockNumber +".txt");
        File fout = new File("C:/eth/addr" + (fileBlockNumber + 1) +"_"+endBlockNumber +".txt");
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

    public boolean ifContract(String addr, Web3j web3j) {
        try{
            Request<?, EthGetCode> ethGetCodeRequest = web3j.ethGetCode(addr, DefaultBlockParameterName.LATEST);
            EthGetCode ethGetCode = ethGetCodeRequest.send();
            byte[] but = ethGetCode.getCode().getBytes();
            if(but.length > 2) {
                return true;
            }
        }catch (Exception e) {
            System.out.println("出现异常--->ifContract");
            e.printStackTrace();
            return true;
        }
        return false;
    }

    public String getEthHttpUrl() {
        return ethHttpUrl;
    }
    public void setEthHttpUrl(String ethHttpUrl) {
        this.ethHttpUrl = ethHttpUrl;
    }

}
