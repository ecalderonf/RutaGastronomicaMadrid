package rutagastronomicamadrid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "rutagastronomicamadrid.feign")
public class GastroMadridApplication {

	public static void main(String[] args) {
		SpringApplication.run(GastroMadridApplication.class, args);
	}

}
