package com.project.gryllo.member.service;

import com.project.gryllo.member.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;





public interface MemberService extends UserDetailsService {

	boolean register(MemberInput parameter);
	boolean emailAuth(String uuid);

}
