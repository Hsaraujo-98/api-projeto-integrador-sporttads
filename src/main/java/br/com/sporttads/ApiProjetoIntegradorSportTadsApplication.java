package br.com.sporttads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "br.com.sporttads.model")
@ComponentScan(basePackages = { "br.*" })
@EnableJpaRepositories(basePackages = { "br.*" })
@EnableTransactionManagement
public class ApiProjetoIntegradorSportTadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProjetoIntegradorSportTadsApplication.class, args);
		System.out.println("API RODANDO!");

	}

}
