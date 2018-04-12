package ru.mitina.laba7;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Laba7ApplicationTests {

	@Test
	public void contextLoads() {
		Laba7Application Laba7Application=new Laba7Application();
		Laba7Application.performSomeTask();
	}

}
