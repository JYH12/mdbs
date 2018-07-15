package mdbs.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mdbs.pojo.User;
import com.mdbs.service.LoginService;

@RunWith(SpringJUnit4ClassRunner.class) //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:application-context.xml","classpath:spring-mvc.xml","classpath:mybatis-config.xml" })
public class TestUserService {

	@Autowired
	LoginService loginService;
	@Test
	public void testLogin() throws Exception{
		User user=new User();
		user.setEmail("123@qq.com");
		user.setPassword("123");
		System.out.println(loginService.login(user));
	}
}
