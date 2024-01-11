package com.mysite.medium.comment.service;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.article.repository.ArticleRepository;
import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.dto.CommentMapper;
import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.comment.repository.CommentRepository;
import com.mysite.medium.global.exception.EntityNotFoundException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.member.entity.Member;
import com.mysite.medium.member.repository.MemberRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final CommentMapper commentMapper;
    private final MemberRepository memberRepository;

    public Page<CommentDto> findCommentAllByArticleId(final int page, final Long id) {
        final List<Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        final Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        final Page<CommentDto> commentPaging = commentRepository.findCommentAll(pageable, id);

        return commentPaging;
    }

    public Long createComment(final Long articleId, final CommentDto commentDto, final Principal principal) {

        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        final Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        final Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .article(article)
                .author(member)
                .build();

        commentRepository.save(comment);

        Long commentId = comment.getId();
        return commentId;
    }

    public CommentDto findCommentByCommendId(final Long commentId) {
        final Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        CommentDto commentDto = commentMapper.commentToCommentDto(comment);
        return commentDto;
    }

    @Transactional
    public void modifyComment(final Long commentId, final CommentDto commentDto, final Principal principal) {
        final Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        comment.checkCommentAuthor(principal);

        comment.modifyComment(commentDto);
    }

    @Transactional
    public void deleteComment(final Long commentId, final Principal principal) {

        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        comment.checkCommentAuthor(principal);

        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteAllByArticleId(final Long articleId) {
        commentRepository.deleteAllByArticleId(articleId);
    }


}