package mx.adea.com.PracticaEM;

import mx.adea.com.PracticaEM.dao.impl.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PracticaEmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticaEmApplication.class, args);
	}

}
