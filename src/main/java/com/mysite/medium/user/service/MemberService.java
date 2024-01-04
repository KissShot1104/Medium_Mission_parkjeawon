package com.mysite.medium.user.service;

import com.mysite.medium.user.dto.CheckLoginDto;
import com.mysite.medium.user.dto.MemberCreateDto;
import java.security.Principal;

public interface MemberService {

    void createUser(final MemberCreateDto memberCreateDto);

    CheckLoginDto checkLogin(final Principal principal);

}
