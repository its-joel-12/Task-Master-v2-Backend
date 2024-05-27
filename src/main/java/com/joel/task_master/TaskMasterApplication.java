package com.joel.task_master;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TaskMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMasterApplication.class, args);
		System.out.println("+---------------------------------------------------------------------------------------+");
		System.out.println("|\t\t\tTASKMASTER APPLICATION HAS STARTED/RESTARTED\t\t\t|");
		System.out.println("+---------------------------------------------------------------------------------------+");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
