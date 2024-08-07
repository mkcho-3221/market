package com.example.wanted.member;

import com.example.wanted.auth.utils.MemberAuthorityUtils;
import com.example.wanted.event.MemberRegistrationApplicationEvent;
import com.example.wanted.exception.BusinessLogicException;
import com.example.wanted.exception.ExceptionCode;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberAuthorityUtils authorityUtils;
    private final ApplicationEventPublisher publisher;

    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder,
                         MemberAuthorityUtils authorityUtils,
                         ApplicationEventPublisher publisher) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this.publisher = publisher;
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

        publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));

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

