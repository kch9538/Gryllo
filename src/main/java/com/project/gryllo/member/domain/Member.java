package com.project.gryllo.member.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "member")
public class Member implements MemberCode{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userId;
	private String nickName;
	private String password;
	private LocalDateTime regDt;

	private boolean emailAuthYn;
	private LocalDateTime emailAuthDt;
	private String emailAuthKey;

	private boolean adminYn;
	private String userStatus;

}
