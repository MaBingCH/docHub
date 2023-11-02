package com.linyi.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	
//	/**
//	 * h2c协议
//	 * @return
//	 */
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//	   TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//	   tomcat.addAdditionalTomcatConnectors(createH2cConnector());
//	   return tomcat;
//	}
//	 
//	private Connector createH2cConnector() {
//	   Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//	   Http2Protocol upgradeProtocol = new Http2Protocol();
//	   connector.addUpgradeProtocol(upgradeProtocol);
//	   connector.setPort(5080);
//	 
//	   return connector;
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}