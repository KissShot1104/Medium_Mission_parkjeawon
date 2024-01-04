package com.mysite.medium.user.service;

import com.mysite.medium.global.exception.AuthException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.user.dto.CheckLoginDto;
import com.mysite.medium.user.dto.MemberCreateDto;
import com.mysite.medium.user.dto.MemberDtoMapper;
import com.mysite.medium.user.entity.Member;
import com.mysite.medium.user.repository.MemberRepository;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberDtoMapper memberDtoMapper;

    @Transactional
    public void createUser(final MemberCreateDto memberCreateDto) {

        memberRepository.findByUsername(memberCreateDto.getUsername())
                .ifPresent((m) -> {
                    throw new AuthException(ErrorCode.DUPLICATED_LOGIN_ID);
                });

        memberRepository.findByEmail(memberCreateDto.getEmail())
                .ifPresent((m) -> {
                    throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
                });

        memberCreateDto.checkEqualsPassword();

        final Member user = Member.builder()
                .username(memberCreateDto.getUsername())
                .email(memberCreateDto.getEmail())
                .password(passwordEncoder.encode(memberCreateDto.getPassword1()))
                .isPaid(memberCreateDto.getIsPaid())
                .build();

        memberRepository.save(user);
    }

    public CheckLoginDto checkLogin(final Principal principal) {
        if (principal == null) {
            throw new AuthException(ErrorCode.IS_NOT_LOGIN);
        }
        final Member member = this.memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new AuthException(ErrorCode.IS_NOT_LOGIN));

        final CheckLoginDto memberDto = memberDtoMapper.memberToCheckLoginDto(member);

        return memberDto;
    }



}