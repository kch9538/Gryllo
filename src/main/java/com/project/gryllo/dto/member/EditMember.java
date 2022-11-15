package com.project.gryllo.dto.member;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class EditMember {

	@NotNull
	private long id;

	@Size(min = 2, max = 30, message = "2~10자")
	private String nickName;

	@Size(min = 2, max = 30, message = "2~10자")
	private String userName;

	@Size(min = 4, max = 50, message = "4~16자")
	private String password;
	private String bio;
	private Optional<MultipartFile> file;

	@Builder
	public EditMember(String nickName, String userName, String password, String bio,
		Optional<MultipartFile> file) {
		this.nickName = nickName;
		this.userName = userName;
		this.password = password;
		this.bio = bio;
		this.file = file;
	}
}
