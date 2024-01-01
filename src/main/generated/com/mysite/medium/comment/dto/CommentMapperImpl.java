package com.mysite.medium.comment.dto;

import com.mysite.medium.comment.dto.CommentDto.CommentDtoBuilder;
import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.comment.entity.Comment.CommentBuilder;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.SiteUserDto.SiteUserDtoBuilder;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.entity.SiteUser.SiteUserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-01T21:29:21+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.1 (Azul Systems, Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto commentToCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDtoBuilder commentDto = CommentDto.builder();

        commentDto.id( comment.getId() );
        commentDto.content( comment.getContent() );
        commentDto.article( comment.getArticle() );
        commentDto.createDate( comment.getCreateDate() );
        commentDto.modifyDate( comment.getModifyDate() );
        commentDto.author( siteUserToSiteUserDto( comment.getAuthor() ) );

        return commentDto.build();
    }

    @Override
    public Comment commentDtoToComment(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        CommentBuilder comment = Comment.builder();

        comment.id( commentDto.getId() );
        comment.content( commentDto.getContent() );
        comment.article( commentDto.getArticle() );
        comment.author( siteUserDtoToSiteUser( commentDto.getAuthor() ) );

        return comment.build();
    }

    protected SiteUserDto siteUserToSiteUserDto(SiteUser siteUser) {
        if ( siteUser == null ) {
            return null;
        }

        SiteUserDtoBuilder siteUserDto = SiteUserDto.builder();

        siteUserDto.id( siteUser.getId() );
        siteUserDto.username( siteUser.getUsername() );
        siteUserDto.password( siteUser.getPassword() );
        siteUserDto.email( siteUser.getEmail() );

        return siteUserDto.build();
    }

    protected SiteUser siteUserDtoToSiteUser(SiteUserDto siteUserDto) {
        if ( siteUserDto == null ) {
            return null;
        }

        SiteUserBuilder siteUser = SiteUser.builder();

        siteUser.id( siteUserDto.getId() );
        siteUser.username( siteUserDto.getUsername() );
        siteUser.password( siteUserDto.getPassword() );
        siteUser.email( siteUserDto.getEmail() );

        return siteUser.build();
    }
}
