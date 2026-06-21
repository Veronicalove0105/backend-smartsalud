package com.policlinico.smartsalud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.policlinico.smartsalud.domain.repository.PacienteRepository;

@SpringBootApplication
public class SmartsaludApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartsaludApplication.class, args);
	}

    @Bean
    public CommandLineRunner initPasswords(PacienteRepository repository, PasswordEncoder encoder) {
        return args -> {
            String newHash = encoder.encode("123456");
            repository.findAll().forEach(paciente -> {
                if (paciente.getEmail().contains("@smartsalud.com")) {
                    paciente.setPasswordHash(newHash);
                    repository.save(paciente);
                    System.out.println("Password updated for: " + paciente.getEmail() + " to '123456'");
                }
            });
        };
    }
}
