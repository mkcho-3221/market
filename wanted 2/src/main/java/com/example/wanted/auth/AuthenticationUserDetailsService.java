package com.example.wanted.auth;

import com.example.wanted.auth.utils.MemberAuthorityUtils;
import com.example.wanted.exception.BusinessLogicException;
import com.example.wanted.exception.ExceptionCode;
import com.example.wanted.member.Member;
import com.example.wanted.member.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class AuthenticationUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberAuthorityUtils authorityUtils;

    public AuthenticationUserDetailsService(MemberRepository memberRepository, MemberAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username);

        Member findMember = optionalMember.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Collection<? extends GrantedAuthority> authorities =
                authorityUtils.createAuthorities(findMember.getEmail());

        return new HelloUserDetails(findMember);
    }

    private final class HelloUserDetails extends Member implements UserDetails{
        HelloUserDetails(Member member){
            setMemberId(member.getMemberId());
            setMemberName(member.getMemberName());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }
    }
}
