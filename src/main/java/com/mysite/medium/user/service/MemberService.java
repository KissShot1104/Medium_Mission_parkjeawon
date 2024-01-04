package com.mysite.medium.user.service;

import com.mysite.medium.user.dto.CheckLoginDto;
import com.mysite.medium.user.dto.MemberCreateDto;

public interface MemberService {

    void createUser(final MemberCreateDto memberCreateDto);

    CheckLoginDto checkLogin(final String username);

}
