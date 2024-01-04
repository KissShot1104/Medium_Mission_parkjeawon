package com.mysite.medium.member.dto;

import com.mysite.medium.member.dto.CheckLoginDto.CheckLoginDtoBuilder;
import com.mysite.medium.member.dto.MemberDto.MemberDtoBuilder;
import com.mysite.medium.member.entity.Member;
import com.mysite.medium.member.entity.Member.MemberBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-04T13:52:11+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.1 (Azul Systems, Inc.)"
)
@Component
public class MemberDtoMapperImpl implements MemberDtoMapper {

    @Override
    public Member memberDtoToMember(MemberDto memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.id( memberDto.getId() );
        member.username( memberDto.getUsername() );
        member.password( memberDto.getPassword() );
        member.email( memberDto.getEmail() );
        member.isPaid( memberDto.getIsPaid() );

        return member.build();
    }

    @Override
    public MemberDto memberToMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDtoBuilder memberDto = MemberDto.builder();

        memberDto.id( member.getId() );
        memberDto.username( member.getUsername() );
        memberDto.password( member.getPassword() );
        memberDto.email( member.getEmail() );
        memberDto.isPaid( member.getIsPaid() );

        return memberDto.build();
    }

    @Override
    public CheckLoginDto memberToCheckLoginDto(Member member) {
        if ( member == null ) {
            return null;
        }

        CheckLoginDtoBuilder checkLoginDto = CheckLoginDto.builder();

        checkLoginDto.username( member.getUsername() );
        checkLoginDto.isPaid( member.getIsPaid() );

        return checkLoginDto.build();
    }
}
