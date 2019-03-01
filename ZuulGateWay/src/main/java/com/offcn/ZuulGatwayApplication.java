package com.offcn;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.offcn.filter.AccessFilter;


@EnableZuulProxy
@SpringCloudApplication
public class ZuulGatwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulGatwayApplication.class, args);
	}
	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}

	

}

