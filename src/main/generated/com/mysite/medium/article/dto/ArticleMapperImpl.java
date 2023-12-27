package com.mysite.medium.article.dto;

import com.mysite.medium.article.dto.ArticleDto.ArticleDtoBuilder;
import com.mysite.medium.article.entity.Article;
import com.mysite.medium.article.entity.Article.ArticleBuilder;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.SiteUserDto.SiteUserDtoBuilder;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.entity.SiteUser.SiteUserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-27T14:47:38+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.1 (Azul Systems, Inc.)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public ArticleDto articleToArticleDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDtoBuilder articleDto = ArticleDto.builder();

        articleDto.id( article.getId() );
        articleDto.subject( article.getSubject() );
        articleDto.content( article.getContent() );
        articleDto.createDate( article.getCreateDate() );
        articleDto.modifyDate( article.getModifyDate() );
        articleDto.author( siteUserToSiteUserDto( article.getAuthor() ) );

        return articleDto.build();
    }

    @Override
    public Article articleDtoToArticle(ArticleDto article) {
        if ( article == null ) {
            return null;
        }

        ArticleBuilder article1 = Article.builder();

        article1.id( article.getId() );
        article1.subject( article.getSubject() );
        article1.content( article.getContent() );
        article1.author( siteUserDtoToSiteUser( article.getAuthor() ) );

        return article1.build();
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
