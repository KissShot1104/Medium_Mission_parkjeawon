package com.mysite.medium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//
//
//@SpringBootTest
//class SbbApplicationTests {
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Transactional
//    @Test
//    void testJpa() {
//        Optional<Article> oq = this.articleRepository.findById(2);
//        assertTrue(oq.isPresent());
//        Article q = oq.get();
//
//        List<Answer> answerList = q.getAnswerList();
//
//        assertEquals(1, answerList.size());
//        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
//    }
//}