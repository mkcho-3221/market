package com.example.wanted.auth.filter;

import com.example.wanted.auth.jwt.JwtTokenizer;
import com.example.wanted.auth.utils.MemberAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final MemberAuthorityUtils authorityUtils;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer, MemberAuthorityUtils authorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    private Map<String, Object> verifyJwt(HttpServletRequest request){
        String jws = request.getHeader("Authorization").replace("Bearer ", "");

        String base64EncodedScretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedScretKey).getBody();

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims){
        String username = (String) claims.get("username");

        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            Map<String, Object> claims = verifyJwt(request);
            setAuthenticationToContext(claims);
        }catch(SignatureException se){
            request.setAttribute("exception", se);
        }catch(ExpiredJwtException ee){
            request.setAttribute("exception", ee);
        }catch(Exception e){
            request.setAttribute("exception",e);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }
}
