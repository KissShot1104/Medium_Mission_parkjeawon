package com.mysite.medium.user;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.article.repository.ArticleRepository;
import com.mysite.medium.article.service.ArticleService;
import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.comment.repository.CommentRepository;
import com.mysite.medium.comment.service.CommentService;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.repository.UserRepository;
import com.mysite.medium.user.service.UserService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DbInit {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    //private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void Init() {



        //Init SiteUser
        List<SiteUser> siteUsers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SiteUser siteUser = SiteUser.builder()
                    .username(i + "")
                    .password(passwordEncoder.encode(i + ""))
                    .email(i + "@" + i)
                    .build();
            siteUsers.add(siteUser);
            userRepository.save(siteUser);
//            userService.createUser(UserCreateDto.builder()
//                    .username(siteUser.getUsername())
//                    .password1(siteUser.getPassword())
//                    .password2(siteUser.getPassword())
//                    .email(siteUser.getEmail())
//                    .build());
        }

        //Init Article
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Article article = Article.builder()
                    .subject(i + "" + i + i)
                    .content(i + "" + i + i)
//                    .author(userService.siteUserFormToSiteUser(siteUsers.get(i%10)))
                    .author(siteUsers.get(1 % 10))
                    .build();

            articles.add(article);
            articleRepository.save(article);
//            articleService.createArticle(articleService.articleToArticleDto(article), siteUsers.get(i%10));
        }

        //Init comment
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Comment comment = Comment.builder()
                    .content(i + "" + i + i + i)
//                    .author(userService siteUsers.get(i % 10))
                    .author(siteUsers.get(i % 10))
                    .article(articles.get(i % 50))
                    .build();

            comments.add(comment);
            commentRepository.save(comment);
        }


    }


}
