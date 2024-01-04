package com.mysite.medium.member;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.article.repository.ArticleRepository;
import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.comment.repository.CommentRepository;
import com.mysite.medium.member.entity.Member;
import com.mysite.medium.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void Init() {



        //Init Member
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Member member = Member.builder()
                    .username(i + "")
                    .password(passwordEncoder.encode(i + ""))
                    .email(i + "@" + i)
                    .isPaid(i % 2 == 0)
                    .build();
            members.add(member);
            memberRepository.save(member);

        }

        //Init Article
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Article article = Article.builder()
                    .subject("제목:" + i + "" + i + i)
                    .content("내용:" + i + "" + i + i)
                    .author(members.get(i % 8))
                    .viewCount((long) i)
                    .build();

            articles.add(article);
            articleRepository.save(article);
        }

        //Init comment
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Comment comment = Comment.builder()
                    .content(i + "" + i + i + i)
//                    .author(userService members.get(i % 10))
                    .author(members.get(i % 7))
                    .article(articles.get(i % 100))
                    .build();

            comments.add(comment);
            commentRepository.save(comment);
        }


    }


}
