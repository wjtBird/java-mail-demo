package com.sinolife.base.configuration;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Created by wjt on 2018/4/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomBeanCfgTest {


    @Autowired
    private ExecutorService executorService;


    @Test
    public void executorServiceTest() throws ExecutionException, InterruptedException {

//        Future future=this.executorService.submit(() -> {
//            System.out.println("call:" + Instant.now().toEpochMilli());
//            return "finish";
//        });
//
//        System.out.println("future:" + Instant.now().toEpochMilli()+"is done:"+future.get());

    }

    @Test
    public void testLazy(){
        System.out.println("testLazy:" + DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()));

    }

}