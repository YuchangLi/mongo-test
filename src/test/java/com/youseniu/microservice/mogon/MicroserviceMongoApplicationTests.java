package com.youseniu.microservice.mogon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicroserviceMongoApplicationTests {

	@Test
	public void contextLoads() {
	}

	  @Autowired
	  private MyService myService;
	  
	@Test
	public void myServiceTest() {
	  myService.doWork();
	}
}
