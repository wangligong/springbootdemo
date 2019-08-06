package com.kernas.block.blockinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by wlg on 2018/6/27.
 */
public class HttpRequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * 发送get请求
     * @param restTemplate
     * @param reqUrl
     * @param reqParam
     * @return json
     */
    public static String sendGetRequest(RestTemplate restTemplate, String reqUrl, Map<String, Object> reqParam){

        logger.debug("get：请求参数->" + reqParam + "url->" + reqUrl);

        String url = reqUrl;

        String rt = restTemplate.getForObject(url, String.class, reqParam);

        logger.debug("请求返回结果:->" + rt);
        return rt;
    }

//    /**
//     * 发送post请求
//     * @param restTemplate
//     * @param reqUrl
//     * @param reqParam
//     * @return json
//     */
//    public static String sendPostRequest(RestTemplate restTemplate, String reqUrl, Map<String, Object> reqParam) {
//
//        logger.debug("post：请求参数->" + reqParam + "url->" + reqUrl);
//
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        HttpEntity<String> formEntity = new HttpEntity<String>(JsonMapper.toJsonString(reqParam), headers);
//
//        String url = BASE_URL + VERSION_CODE + reqUrl;
//
//        String rt = restTemplate.postForObject(url, formEntity, String.class);
//        logger.debug("返回结果:" + rt);
//        return rt;
//    }
}
