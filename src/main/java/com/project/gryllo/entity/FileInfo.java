package com.project.gryllo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class FileInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String fileName;

	@Column(nullable = false)
	private String filePath;

	@Builder
	public FileInfo(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "FileInfo{" +
			"id=" + id +
			", fileName='" + fileName + '\'' +
			", filePath='" + filePath + '\'' +
			'}';
	}
}
