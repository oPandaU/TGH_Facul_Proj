package com.the_gathering_hub.TGH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TghApplication {

    public static void main(String[] args) {
        SpringApplication.run(TghApplication.class, args);
        System.out.println("========================================");
        System.out.println("Aplicação iniciada com sucesso!");
        System.out.println("API disponível em: http://localhost:8080/api");
        System.out.println("Abra o arquivo index.html no navegador");
        System.out.println("========================================");
    }
}
