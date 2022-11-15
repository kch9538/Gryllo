package com.project.gryllo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Member extends BasicEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Email
	@NotBlank
	@Column(nullable = false, unique = true)
	private String email;

	@Size(min = 2, max = 30)
	@Column(nullable = false, unique = true)
	private String nickName;

	@Size(min = 2, max = 30)
	@Column(nullable = false)
	private String userName;

	@Size(min = 4, max = 30)
	@Column(nullable = false)
	private String password;

	@Lob
	private String bio;

	@OneToOne
	@JoinColumn(name = "FILEINFO_ID")
	private FileInfo fileInfo;

	@Column(columnDefinition = "boolean default false")
	private boolean isOauthUser;

	@Builder
	public Member(String email, String nickName, String userName, String password, String bio, FileInfo fileInfo, boolean isOauthUser) {
		this.email = email;
		this.nickName = nickName;
		this.userName = userName;
		this.password = password;
		this.bio = bio;
		this.fileInfo = fileInfo;
		this.isOauthUser = isOauthUser;
	}

	public void update(Member updateMember, String sessionEmail) {
		checkEmail(sessionEmail);
		this.nickName = updateMember.nickName;
		this.userName = updateMember.userName;
		this.password = updateMember.password;
		this.bio = updateMember.bio;
		this.fileInfo = updateMember.fileInfo;
	}

	public void changeToOAuthUser() {
		if (!this.isOauthUser) {
			this.isOauthUser = true;
		}
	}

	public void checkPassword(String password) {
		if (!this.password.equals(password)) {
			throw new RuntimeException("회원정보가 일치하지 않습니다.");
		}
	}

	public void checkEmail(String email) {
		if (!this.email.equals(email)) {
			throw new RuntimeException("회원정보가 일치하지 않습니다.");
		}
	}

	public boolean equalsNickName(String nickName) {
		return this.nickName.equals(nickName);
	}

	public boolean isNotSameUser(long id) {
		return this.id != id;
	}

	public boolean isSameUser(Member other) {
		return this.id == other.id;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", email='" + email + '\'' +
			", nickName='" + nickName + '\'' +
			", userName='" + userName + '\'' +
			", password='" + password + '\'' +
			", bio='" + bio + '\'' +
			", fileInfo=" + fileInfo +
			", isOauthUser=" + isOauthUser +
			'}';
	}
}
