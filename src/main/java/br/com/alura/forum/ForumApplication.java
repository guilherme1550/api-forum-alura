package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport //Anotação que possibilita receber um objeto de paginação no parametro de determinado método
@EnableCaching
@EnableSwagger2
public class ForumApplication extends SpringBootServletInitializer /*Necessário estender essa classe para criar o .war*/ {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}
	
//	@Override //Necessário esse método para criar o .war
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(ForumApplication.class);
//	}
}
