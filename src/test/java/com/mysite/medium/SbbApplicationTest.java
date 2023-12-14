package com.mysite.medium;

import com.mysite.medium.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@Commit
class SbbApplicationTest {

    @Autowired
    private ArticleRepository articleRepository;

    /*@Test
    public void init() {
        for (int i = 0; i < 156; i++) {
            questionRepository.save(Question.builder()
                    .subject("test Subject " + i)
                    .content("test Content " + i)
                    .createDate(LocalDateTime.now())
                    .build());
        }
    }*/

}