package tech.ftchekure.documentslostandfound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DocumentsLostAndFoundApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentsLostAndFoundApplication.class, args);
	}

}
