package com.example.wanted.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPatchDto{
    private long memberId;
    private String userName;
    private String password;
}
