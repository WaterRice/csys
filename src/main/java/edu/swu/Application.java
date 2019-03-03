/**  

* 创建时间：2018年12月8日 下午2:50:59  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：Application.java  

* 类说明：  启动类

*/

package edu.swu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
	
	@Bean
	public ConfigurableServletWebServerFactory containerCustomizer() {
	  TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	  factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/index.html"));
	  return factory;
	}
}
