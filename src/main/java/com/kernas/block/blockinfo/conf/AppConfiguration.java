package com.kernas.block.blockinfo.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * Created by wlg on 2017/12/26.
 */
@Configuration
public class AppConfiguration {

    @Value("${geth.ethHttpUrl}")
    private String ethHttpUrl;
    @Bean
    public Web3j getWeb3j() {
        System.out.print(ethHttpUrl + "<-----------------------------");
        return org.web3j.protocol.Web3j.build(new HttpService(getEthHttpUrl()));
    }

    public String getEthHttpUrl() {
        return ethHttpUrl;
    }
    public void setEthHttpUrl(String ethHttpUrl) {
        this.ethHttpUrl = ethHttpUrl;
    }
}
