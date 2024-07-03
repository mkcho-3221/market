package com.example.wanted.config;

import com.example.wanted.member.MemberRepository;
import com.example.wanted.member.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JavaConfiguration {
    @Bean
    public MemberService dbMemberService(MemberRepository memberRepository,
                                         PasswordEncoder passwordEncoder){
        return new MemberService(memberRepository, passwordEncoder);
    }
}
