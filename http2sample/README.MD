# keystore以及服务器密钥对儿的生成：
keytool -genkeypair -alias keystore -keyalg RSA -validity 3650 -keystore keystore.jks
psw: keystore
# 验证新生成的keystore文件以及证书信息
keytool -list -v -keystore keystore.jks
# 导出公钥证书
keytool -export -alias keystore -keystore keystore.jks -rfc -file cert.cer
# Truststore的生成以及公钥证书的导入
keytool -import -alias keystore -file cert.cer -keystore truststore.jks
# 验证生成的truststore文件
keytool -list -v -keystore truststore.jks

# http2
https://docs.spring.io/spring-boot/docs/2.0.x-SNAPSHOT/reference/htmlsingle/#howto-configure-http2-jetty

application.yml
server.ssl.key-store: classpath:keystore.jks
server.ssl.key-store-password: keystore
server.ssl.key-password: keystore
server.servlet.context-path: /demo
server.http2.enabled: true

server.tomcat.basedir: my-tomcat
server.tomcat.accesslog.enabled: true
server.tomcat.accesslog.pattern: "%t %a %r %s (%D ms)"

# springboot h2c support
```
	/**
	 * h2c协议
	 * @return
	 */
	@Bean
	public ServletWebServerFactory servletContainer() {
	   TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
	   tomcat.addAdditionalTomcatConnectors(createH2cConnector());
	   return tomcat;
	}
	 
	private Connector createH2cConnector() {
	   Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	   Http2Protocol upgradeProtocol = new Http2Protocol();
	   connector.addUpgradeProtocol(upgradeProtocol);
	   connector.setPort(5080);
	 
	   return connector;
	}
```
# curl 
curl -vvv --http2 http://127.0.0.1:5080/demo/users/123