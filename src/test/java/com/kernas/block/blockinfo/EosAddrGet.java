package com.kernas.block.blockinfo;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wlg on 2019/1/16.
 */
public class EosAddrGet {

    @Test
    public void handleAddr(){
            List<String> list = new ArrayList<String>();
            list.add("C:/addrEos/eosaddrquchong.txt");

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

            String json = JsonMapper.toJsonString(addrSet);

            System.out.println(json);

            try {
                writeAddrFileBySetJS(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("write finsh");
    }


    @Test
    public void getEosAddr() throws ParseException {
        //获取地址
        //1小时=3600s

        String startTime = "2019-01-15 00:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Set<String> addrSet = new HashSet<String>();
//        System.out.println(String.valueOf(sdf.parse(startTime).getTime()/1000));
        Set<String> bodySet = new HashSet<>();

        long startTimeL = sdf.parse(startTime).getTime() / 1000;
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());

        try{
            for (int i = 0; i < 200; i++) {
                System.out.println(i);

                String url = "https://dice.one/dice/prod/api/contest/3/" + startTimeL + "/EOS";
            System.out.println(url);
                String postBody = "";

                HttpHeaders headers = new HttpHeaders();
                headers.add("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36");
                HttpEntity<String> entity = new HttpEntity<>(postBody, headers);

                //String var1, HttpMethod var2, HttpEntity<?> var3, Class<T> var4, Object... var5
                ResponseEntity<String> json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, postBody);
                String body = json.getBody();
                bodySet.add(body);
                startTimeL = startTimeL + 3600;
            }
        }catch (Exception e) {
            System.out.println(e);
        }

        Iterator itbody = bodySet.iterator();
        while (itbody.hasNext()){
            try{
                String body = (String) itbody.next();
                Map<String, Object> bodyMap = (Map<String, Object>) JsonMapper.fromJsonString(body, Map.class);
                List<Object> list = (List<Object>) bodyMap.get("list");

                System.out.println(list);

                if(list != null) {
                    Iterator it = list.iterator();

                    while (it.hasNext()) {
                        Map<String, Object> mapObj = (Map<String, Object>) it.next();
//                System.out.println(mapObj.get("name"));
                        addrSet.add((String) mapObj.get("name"));
                    }
                }
            }catch (Exception e) {
                System.out.println(e);
            }

        }


        try {
            writeAddrFileBySet(addrSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("write finsh");
    }


    @Test
    public void getEosAddr2() throws ParseException {
        String url = "https://b589fb6b-d6ad-497e-89fe-6551822d4fe6.gw.eosinfra.io/v1/history/get_actions";
//        String refer = "https://www.eosx.io/account/okbtothemoon?sub=actions&page=" + i;
//        System.out.println(url);

        Map<String, String> map = new HashMap<>();
        map.put("account_name", "okbtothemoon");
        map.put("counter", "1");
        map.put("offset", "-101");
        map.put("pos", "-1");

        String postBody = JsonMapper.toJsonString(map);

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36");
        headers.add("Content-Type", "text/plain");
        headers.add("Origin","https://www.eosx.io");
        headers.add("Referer", "https://www.eosx.io/account/okbtothemoon?sub=actions&page=2857");
        HttpEntity<String> entity = new HttpEntity<>(postBody, headers);
//        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        RestTemplate restTemplate = new RestTemplate();
        //String var1, HttpMethod var2, HttpEntity<?> var3, Class<T> var4, Object... var5
        ResponseEntity<String> json = restTemplate.exchange(url, HttpMethod.POST, entity, String.class, postBody);
        String body = json.getBody();
        System.out.println(body);

    }


    @Test
    public void getEosAddr10() throws ParseException, IOException {
//        String url = "https://b589fb6b-d6ad-497e-89fe-6551822d4fe6.gw.eosinfra.io/v1/history/get_actions";
//        String url = "https://history.cryptolions.io/v1/history/get_actions";
        String url = "https://b589fb6b-d6ad-497e-89fe-6551822d4fe6.gw.eosinfra.io/v1/history/get_actions";
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        List<String> bodys = new ArrayList<>();
        Set<String> addr = new HashSet<>();

        try{
            for(int i = 2798; i > 2796; i--) {
                String refer = "https://www.eosx.io/account/okbtothemoon?sub=actions&page=" + i;
                System.out.println(i);

                Map<String, String> map = new HashMap<>();
                map.put("account_name", "okbtothemoon");
                map.put("counter", "1");
                map.put("offset", "-101");
                map.put("pos", "-1");

                String postBody = JsonMapper.toJsonString(map);

                HttpHeaders headers = new HttpHeaders();
                headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
                headers.add("Content-Type", "text/plain");
                headers.add("Origin","https://www.eosx.io");
                headers.add("Referer", refer);
                headers.add("Accept", "application/json, text/plain, */*");
                HttpEntity<String> entity = new HttpEntity<>(postBody, headers);
                //String var1, HttpMethod var2, HttpEntity<?> var3, Class<T> var4, Object... var5
                ResponseEntity<String> json = restTemplate.exchange(url, HttpMethod.POST, entity, String.class, postBody);
                String body = json.getBody();
                bodys.add(body);
//                System.out.println(body);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bodys.size());

        try {
            Iterator bodyit = bodys.iterator();
            while (bodyit.hasNext()){
                String body = (String) bodyit.next();
                Map<String, Object> data = (Map<String, Object>) JsonMapper.fromJsonString(body, Map.class);

                List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("actions");
                Iterator it = list.iterator();
                while (it.hasNext()){
                    Map<String, Object> act = (Map<String, Object>) it.next();
                    if(act != null) {
                        Map<String, Object> actchild = (Map<String, Object>) act.get("action_trace");
                        if(actchild != null) {
                            Map<String, Object> actMap = (Map<String, Object>) actchild.get("act");
                            if(actMap != null) {
                                Map<String, Object> fromTo = (Map<String, Object>) actMap.get("data");
                                if(fromTo != null) {
                                    String to = (String) fromTo.get("to");
                                    if(!to.equals("okbtothemoon")){
                                        addr.add(to);
                                    }
                                    String from = (String) fromTo.get("from");
                                    if(!from.equals("okbtothemoon")){
                                        addr.add(from);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(addr);

        writeAddrFileBySet(addr);
    }


    public void writeAddrFileBySetJS(String json) throws IOException {
        File fout = new File("C:/addrEos/JS.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(json);
        bw.close();
    }

    public void writeAddrFileBySet(Set<String> set) throws IOException {
        File fout = new File("C:/addrEos/betdice124.txt");
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


    @Test
    public void getEosAddrEndLess() throws ParseException {
        Set<String> addrSet = new HashSet<String>();
//        System.out.println(String.valueOf(sdf.parse(startTime).getTime()/1000));
        Set<String> bodySet = new HashSet<>();
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        try{
            for (int i = 0; i < 20; i++) {
                System.out.println(i);

                String url = "https://endless.game/api/dailyLeaderBoard?date_offset="+ i +"&symbol=EOS";
//            System.out.println(url);
                String postBody = "";

                HttpHeaders headers = new HttpHeaders();
                headers.add("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36");
                HttpEntity<String> entity = new HttpEntity<>(postBody, headers);

                //String var1, HttpMethod var2, HttpEntity<?> var3, Class<T> var4, Object... var5
                ResponseEntity<String> json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, postBody);
                String body = json.getBody();
                bodySet.add(body);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        Iterator itbody = bodySet.iterator();
        while (itbody.hasNext()){
            try{
                String body = (String) itbody.next();
                Map<String, Object> bodyMap = (Map<String, Object>) JsonMapper.fromJsonString(body, Map.class);
                Map<String, Object> datMap = (Map<String, Object>) bodyMap.get("data");

                List<Object> list = (List<Object>) datMap.get("List");

                System.out.println(list);

                if(list != null) {
                    Iterator it = list.iterator();

                    while (it.hasNext()) {
                        Map<String, Object> mapObj = (Map<String, Object>) it.next();
//                System.out.println(mapObj.get("name"));
                        addrSet.add((String) mapObj.get("Account"));
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(addrSet);
        try {
            writeAddrFileBySet(addrSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("write finsh");
    }

}
