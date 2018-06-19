package com.hc.hclvzh;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HclvzhApplication extends SpringBootServletInitializer /* implements CommandLineRunner */ {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HclvzhApplication.class, args);

		System.out.println("hohoho");
		String[] names = context.getBeanDefinitionNames();
		Arrays.sort(names);
		for (String string : names) {
			System.err.println(string);
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		// return super.configure(builder);
		return builder.sources(HclvzhApplication.class);
	}

	/*
	 * @Override public void run(String... args) throws Exception { RestTemplate
	 * template = new RestTemplate(); Greeting greeting =
	 * template.getForObject("http://localhost:8080/greeting?name=323",
	 * Greeting.class); System.err.println(greeting); }
	 */
}
