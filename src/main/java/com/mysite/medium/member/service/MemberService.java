package com.mysite.medium.member.service;

import com.mysite.medium.member.dto.CheckLoginDto;
import com.mysite.medium.member.dto.MemberCreateDto;
import java.security.Principal;

public interface MemberService {

    void createUser(final MemberCreateDto memberCreateDto);

    CheckLoginDto checkLogin(final Principal principal);

}
