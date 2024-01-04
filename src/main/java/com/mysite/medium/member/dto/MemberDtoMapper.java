package com.mysite.medium.member.dto;


import com.mysite.medium.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberDtoMapper {
    Member memberDtoToMember(MemberDto memberDto);
    MemberDto memberToMemberDto(Member member);
    CheckLoginDto memberToCheckLoginDto(Member member);
}
