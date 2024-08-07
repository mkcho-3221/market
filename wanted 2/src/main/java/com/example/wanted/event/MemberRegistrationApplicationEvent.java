package com.example.wanted.event;

import com.example.wanted.member.Member;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberRegistrationApplicationEvent extends ApplicationEvent {
    private Member member;

    public MemberRegistrationApplicationEvent(Object source,
                                              Member member) {
        super(source);
        this.member = member;
    }
}
