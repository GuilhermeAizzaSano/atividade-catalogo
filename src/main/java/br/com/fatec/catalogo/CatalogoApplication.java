package br.com.fatec.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class CatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void abrirNavegador() {
		String url = "http://localhost:8080";

		System.out.println("\n🚀 Aplicação rodando em:");
		System.out.println("👉 " + url + "\n");

		try {
			Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
		} catch (Exception e) {
			System.out.println("Acesse manualmente: " + url);
		}
	}
}