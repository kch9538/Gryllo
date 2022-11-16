package com.project.gryllo.service.rule;

public interface FileNaming {
	String makeUniquePrefix(String originalFilename);

	String makePath();
}
