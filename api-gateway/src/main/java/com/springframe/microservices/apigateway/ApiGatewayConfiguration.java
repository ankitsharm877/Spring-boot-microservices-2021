package com.springframe.microservices.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		Function<GatewayFilterSpec, UriSpec> fn =
				f -> f.addRequestHeader("MyHeader", "MyUri")
				.addRequestParameter("MyParam", "ParamValue");
		return builder.routes()
					.route("path_route", r -> r.path("/get")
						.filters(fn)
						.uri("http://httpbin.org"))
					.route(p -> p.path("/currency-exchange/**")
						.uri("lb://currency-exchange"))
					.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
					.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))
					.route(p -> p.path("/currency-conversion-new/**")
							.filters(f -> f.rewritePath(
									"/currency-conversion-new/(?<segment>.*)", 
									"/currency-conversion-feign/${segment}"))
							.uri("lb://currency-conversion"))
					.build();
	}
}
