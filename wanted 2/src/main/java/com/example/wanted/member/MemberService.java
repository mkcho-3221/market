package com.example.wanted.member;

import com.example.wanted.auth.utils.MemberAuthorityUtils;
import com.example.wanted.exception.BusinessLogicException;
import com.example.wanted.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private MemberAuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member findVerifiedMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

    public void verifyExistsEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }


    public Member createMember(Member member){

        verifyExistsEmail(member.getEmail());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());

        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }
    public Member updateMember(Member member){

        Member findMember = findMember(member.getMemberId());

        Optional.ofNullable(findMember.getMemberName())
                .ifPresent(userName -> findMember.setMemberName(userName));
        Optional.ofNullable(findMember.getPassword())
                .ifPresent(password -> findMember.setPassword(password));

        Member savedMember = memberRepository.save(findMember);

        return savedMember;
    }

    public Member findMember(long memberId){

        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size){

        return memberRepository.findAll(
                PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    public void deleteUser(long memberId){
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }
}

