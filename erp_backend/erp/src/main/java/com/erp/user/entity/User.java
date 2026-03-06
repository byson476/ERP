package com.erp.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.erp.dto.UserDto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor

@Entity
@Table(name = "USERINFO") // MariaDB 대소문자 구분 설정에 따라 주의
public class User {

	@Id
	@Column(name = "USERID", length = 50) // ID 길이를 명확히 제한 (인덱스 성능)
	private String userId;

	@Column(nullable = false) // 비밀번호는 필수
	private String password;

	@Column(length = 100)
	private String name;

	@Column(unique = true, length = 100)
	private String email;

	@Column(columnDefinition = "TINYINT(0)") 
	private boolean social;

	@Column(nullable = false, length = 50)
	private String department; // 부서

	@Column(nullable = false, length = 30)
	private String position;   // 직급

	private java.time.LocalDate hireDate; // 입사일

	@Column(precision = 15, scale = 2)
	private java.math.BigDecimal salary;  // 급여
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "USERINFO_ROLES", joinColumns = @JoinColumn(name = "USERID"))
    @Enumerated(EnumType.STRING) // 반드시 추가: Enum의 이름(ROLE_USER 등) 그대로 DB에 저장
    @Column(name = "ROLES")
    @Builder.Default
    private List<UserRole> roles = new ArrayList<>();
/*
	// final을 제거하고 @Builder.Default를 유지하여 초기화 보장
	@OneToMany(mappedBy = "user")
	@Builder.Default
	private List<Student> students = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@Builder.Default
	private List<Tutor> tutors = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@Builder.Default
	private List<Admin> admins = new ArrayList<>();
	*/
	public void addRole(UserRole userRole) {
		roles.add(userRole);
	}

	public static User toEntity(UserDto userDto) {
		return User.builder()
				.userId(userDto.getUserId())
				.password(userDto.getPassword())
				.name(userDto.getName())
				.email(userDto.getEmail())
				.social(userDto.isSocial())
				.roles(userDto.getRoles())
				.build();
	}
}

