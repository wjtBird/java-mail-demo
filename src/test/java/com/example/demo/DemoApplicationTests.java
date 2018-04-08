package com.example.demo;

import com.sinolife.mailbusiness.service.AccessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private AccessService accessService;

	private String account = "jintao.wang001@sino-life.com";

	private String password = "Sinolife1";
	@Test
	public void contextLoads() {
	}

	@Test
	public void listFirstTenItemsTest() throws Exception {
		this.accessService.listFirstTenItems("jintao.wang001@sino-life.com","Sinolife1");
	}

	@Test
	public void sendMsgTest() throws Exception {
		this.accessService.sendMsg(this.account,this.password);
	}

	@Test
	public void readMsgTest() throws Exception {
		this.accessService.readMeg(this.account,this.password);
	}

}
