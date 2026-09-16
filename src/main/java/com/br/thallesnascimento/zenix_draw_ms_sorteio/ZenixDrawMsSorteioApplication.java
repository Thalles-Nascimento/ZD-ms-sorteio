package com.br.thallesnascimento.zenix_draw_ms_sorteio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZenixDrawMsSorteioApplication {

	public static void main(String[] args) {
		System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
		SpringApplication.run(ZenixDrawMsSorteioApplication.class, args);
	}

}
