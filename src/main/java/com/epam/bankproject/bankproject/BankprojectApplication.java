package com.epam.bankproject.bankproject;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.impl.Operation;
import com.epam.bankproject.bankproject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootApplication
public class BankprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankprojectApplication.class, args);
	}

}
