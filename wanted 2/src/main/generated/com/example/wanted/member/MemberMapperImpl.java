package com.example.wanted.member;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-04T11:52:49+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostDtoToMember(MemberPostDto memberPostDto) {
        if ( memberPostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setEmail( memberPostDto.getEmail() );
        member.setMemberName( memberPostDto.getMemberName() );
        member.setPassword( memberPostDto.getPassword() );

        return member;
    }

    @Override
    public Member memberPatchDtoToMember(MemberPatchDto memberPatchDto) {
        if ( memberPatchDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( memberPatchDto.getMemberId() );
        member.setMemberName( memberPatchDto.getMemberName() );
        member.setPassword( memberPatchDto.getPassword() );

        return member;
    }

    @Override
    public MemberResponseDto memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        long memberId = 0L;
        String email = null;
        String memberName = null;
        String password = null;

        if ( member.getMemberId() != null ) {
            memberId = member.getMemberId();
        }
        email = member.getEmail();
        memberName = member.getMemberName();
        password = member.getPassword();

        MemberResponseDto memberResponseDto = new MemberResponseDto( memberId, email, memberName, password );

        return memberResponseDto;
    }
}
