package com.linyi.sample;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class Http2ClientSample {

	// 服务器ip
	public static String host = "https://127.0.0.1";

	// 服务器端口
	public static int port = 9001;

	private static OkHttpClient client;

	static {
		try {
			initClient();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// GET请求接口不带参数
	public static String doGetNoParams(String path) {
		String result;
		try {
			String url = host + ":" + port + path;
			Request request = new Request.Builder().url(url).get().build();

			Response response = client.newCall(request).execute();
			result = response.body().string();
			String protocol = response.protocol().name();
			System.out.println("server result:" + result);
			System.out.println("client protocol:" + protocol);
		} catch (IOException e) {
			result = "failed";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 初始化生成OkHttpClient client
	 */
	public static void initClient() throws Exception {
		// 导入公钥证书
		KeyStore keyStore = KeyStore.getInstance("JKS"); // .jks格式
		ClassPathResource resource = new ClassPathResource("keystore.jks");// 路径基于resources
		keyStore.load(resource.getInputStream(), "keystore".toCharArray());// 密钥口令

		// 初始化证书管理factory
		TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		factory.init(keyStore);

		// 获得X509TrustManager
		TrustManager[] trustManagers = factory.getTrustManagers();
		X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

		// 初始化sslContext
		SSLContext sslContext = SSLContext.getInstance("TLS"); // 这里SSL\TLS都可以
		sslContext.init(null, new TrustManager[] { trustManager }, null);

		// 获得sslSocketFactory
		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

		// 初始化client builder
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		// 关键步骤，在这里将HTTP/2协议添加进去
		List<Protocol> protocols = new ArrayList<>();
		protocols.add(Protocol.HTTP_1_1); // 这里如果，只指定h2的话会抛异常
		protocols.add(Protocol.HTTP_2); // 这里如果，只指定h2的话会抛异常
		builder.sslSocketFactory(sslSocketFactory, trustManager).protocols(protocols) // 设置builder protocols
				.hostnameVerifier(new HostnameVerifier() { // 放过host验证
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});

		// build client
		client = builder.build();
	}

	// 创建自定义 SSL 上下文，可用于配置 SSL 参数
	private static SSLContext createCustomSSLContext() {
		try {

			Resource keyStoreFile = new ClassPathResource("keystore.jks");
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(keyStoreFile.getInputStream(), "keystore".toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(keyStore);

			// 获得X509TrustManager
			TrustManager[] trustManagers = tmf.getTrustManagers();
			X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tmf.getTrustManagers(), new SecureRandom());

			return ctx;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[0];
		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws CertificateException {
		}

	} };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		doGetNoParams("/demo/users/123");

		try {
//			// System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
			HttpClient httpClient = HttpClient.newBuilder()
					.sslContext(createCustomSSLContext())
					.build();

			HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://localhost:9001/demo/users/345"))
					.version(HttpClient.Version.HTTP_2).GET().build();
			HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println("Response : " + resp.body());
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * try { Resource keyStoreFile = new ClassPathResource("keystore.jks"); KeyStore
		 * keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		 * keyStore.load(keyStoreFile.getInputStream(), "keystore".toCharArray());
		 * 
		 * TrustManagerFactory tmf =
		 * TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		 * tmf.init(keyStore);
		 * 
		 * // 获得X509TrustManager TrustManager[] trustManagers = tmf.getTrustManagers();
		 * X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
		 * 
		 * SSLContext ctx = SSLContext.getInstance("TLS"); ctx.init(null,
		 * tmf.getTrustManagers(), null);
		 * 
		 * OkHttpClient okHttpClient = new OkHttpClient .Builder()
		 * .sslSocketFactory(ctx.getSocketFactory(), trustManager) .hostnameVerifier((s,
		 * sslSession) -> true) .build(); RestTemplate http2Template = new
		 * RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
		 * 
		 * 
		 * String http2Response =
		 * http2Template.getForObject("https://127.0.0.1:9001/demo/users/234",
		 * String.class); System.out.println(http2Response); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

//		doGetHttp(); PKIX path building failed:
	}

//	public static void doGetHttp() {
//		 OkHttpClient client = new OkHttpClient();
//
//	        Request request = new Request.Builder()
//	            .url("https://127.0.0.1:9001/demo/users/123")
//	            .get()
//	            .build();
//
//	        try {
//	            Response response = client.newCall(request).execute();
//	            String responseBody = response.body().string();
//	            System.out.println("Response: " + responseBody);
//	            System.out.println("protocol: " + response.protocol().name());
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	}

}
