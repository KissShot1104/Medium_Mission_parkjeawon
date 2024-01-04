package com.mysite.medium.user.dto;

import com.mysite.medium.user.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberDtoMapper {
    Member memberDtoToMember(MemberDto memberDto);
    MemberDto memberToMemberDto(Member member);
    CheckLoginDto memberToCheckLoginDto(Member member);
}
