package com.linyi.sample;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Http2Main {

	public static void main(String[] args) {

		try {
			HttpClient httpClient = HttpClient.newBuilder().build();

			HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://127.0.0.1:5080/demo/users/999"))
					.version(HttpClient.Version.HTTP_2).GET().build();
			HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println("Response : " + resp.body());
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
