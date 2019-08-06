package com.kernas.block.blockinfo.controller;

import com.kernas.block.blockinfo.service.ScanBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wlg on 2017/12/25.
 */
@RestController
@RequestMapping("/kernas")
public class RSController {

    @Autowired
    private ScanBlock scanBlock;

    private final static Logger logger = LoggerFactory.getLogger(RSController.class);

    @RequestMapping("/test")
    public String test() {
        return "hello";
    }

    @RequestMapping("/test2")
    public String test2() {
        logger.debug("--------------准备扫描区块-----------------");
        scanBlock.scanBlock();
        return "success";
    }
}
