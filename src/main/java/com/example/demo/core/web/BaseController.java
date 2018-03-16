package com.example.demo.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller父类
 */
public class BaseController {


    /**
     * 日志对象
     */
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 返回码
     */
    protected String SUCCESS = "200";
    protected String FAIL = "500";

}
