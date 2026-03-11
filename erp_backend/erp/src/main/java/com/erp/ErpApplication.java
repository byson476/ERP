package com.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.erp.user.entity.User;
import com.erp.user.entity.UserRole;
import com.erp.user.repository.UserRepository;

@SpringBootApplication
public class ErpApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args){
		SpringApplication.run(ErpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = User.builder()
				.userId("erp1")
				.name("일반회원")
				.email("guard1@gmail.com")
				.password(passwordEncoder.encode("1111")).social(false)
				.department("일반회원")
				.position("사원")
				.build();
		user1.addRole(UserRole.USER);
		User user2 = User.builder()
				.userId("erp2")
				.name("인사팀")
				.email("guard2@gmail.com")
				.password(passwordEncoder.encode("2222")).social(false)
				.department("인사팀")
				.position("사원")
				.build();
		user2.addRole(UserRole.HR_USER);
		User user3 = User.builder()
				.userId("erp3")
				.name("재무팀")
				.email("guard3@gmail.com")
				.password(passwordEncoder.encode("3333")).social(false)
				.department("재무팀")
				.position("사원")
				.build();
		user3.addRole(UserRole.FINANCE_USER);
		User user4 = User.builder()
				.userId("erp4")
				.name("재고관리팀")
				.email("guard4@gmail.com")
				.password(passwordEncoder.encode("4444")).social(false)
				.department("재고관리팀")
				.position("사원")
				.build();
		user4.addRole(UserRole.STOCK_USER);
		User user5 = User.builder()
				.userId("erp4")
				.name("관리자")
				.email("guard5@gmail.com")
				.password(passwordEncoder.encode("5555")).social(false)
				.department("관리자")
				.position("사원")
				.build();
		user5.addRole(UserRole.ADMIN);

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
		userRepository.save(user5);
	}

}