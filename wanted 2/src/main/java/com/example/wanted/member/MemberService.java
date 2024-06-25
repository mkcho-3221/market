package com.example.wanted.member;

import com.example.wanted.exception.BusinessLogicException;
import com.example.wanted.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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

        Member createdMember = memberRepository.save(member);

        return createdMember;
    }

    public Member updateMember(Member member){

        Member findMember = findMember(member.getMemberId());

        Optional.ofNullable(findMember.getUserName())
                .ifPresent(userName -> findMember.setUserName(userName));
        Optional.ofNullable(findMember.getPassword())
                .ifPresent(password -> findMember.setPassword(password));

        Member updatedMember = memberRepository.save(findMember);

        return updatedMember;
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

