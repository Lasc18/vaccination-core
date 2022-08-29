package ec.kruger.vaccination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"ec.kruger.vacunacion.model.person", "ec.kruger.vacunacion.model.common",
		"ec.kruger.vacunacion.model.company", "ec.kruger.vacunacion.model.security"})
@EnableJpaRepositories(basePackages = "ec.kruger.vacunacion.repository")
public class VacunacionCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacunacionCoreApplication.class, args);
	}

}
