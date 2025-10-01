package com.gestionTurno;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API de Gestión de Turnos",
				version = "1.0",
				description = "Documentación de la API para gestión de turnos en una clínica"
		)
)
public class ConsultorioTurnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultorioTurnosApplication.class, args);
	}

}
