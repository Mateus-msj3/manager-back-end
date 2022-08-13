package com.code.managerbackend;

import com.code.managerbackend.model.Permission;
import com.code.managerbackend.model.User;
import com.code.managerbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ManagerBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerBackEndApplication.class, args);
	}


}
