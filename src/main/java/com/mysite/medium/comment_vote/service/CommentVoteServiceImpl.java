package com.mysite.medium.comment_vote.service;


import com.mysite.medium.article.service.ArticleService;
import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.comment.repository.CommentRepository;
import com.mysite.medium.comment.service.CommentService;
import com.mysite.medium.comment_vote.dto.CommentVoteDto;
import com.mysite.medium.comment_vote.entity.CommentVote;
import com.mysite.medium.comment_vote.repository.CommentVoteRepository;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.repository.UserRepository;
import com.mysite.medium.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentVoteServiceImpl implements CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;

    @Transactional
    public void createCommentVote(final Long commentId, final String username) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new EntityNotFoundException("Comment Entity Not Found");
        }

        Optional<SiteUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User Entity Not Found");
        }

        if (isDuplicateRecommendationVoter(commentId, user.get().getId())) {
            throw new IllegalArgumentException("Duplicate Vote Comment");
        }

        CommentVote commentVote = CommentVote.builder()
                .comment(comment.get())
                .user(user.get())
                .build();

        commentVoteRepository.save(commentVote);
    }

    @Transactional
    public void deleteCommentVoteAllByCommentId(Long commentId) {
        commentVoteRepository.deleteCommentVoteAllByCommentId(commentId);
    }

//    public Map<CommentDto, Long> getCommentLikesForArticle(Long articleId) {
//        List<Comment> comments = commentRepository.findAllByArticleId(articleId);
//        Map<CommentDto, Long> commentDtoLikes = new HashMap<>();
//        for (Comment comment: comments) {
//            Long voteCount = commentVoteRepository.countByCommentId(comment.getId());
//            CommentDto commentDto = commentService.commentToCommentDto(comment);
//            commentDtoLikes.put(commentDto, voteCount);
//        }
//        return commentDtoLikes;
//    }

    //이렇게 댓글의 추천수를 받아오는 것보다 더 좋은 방법이 있는가?
    public Map<Long, Long> getCommentLikesForArticle(Long articleId) {
        List<Comment> comments = commentRepository.findAllByArticleId(articleId);
        Map<Long, Long> commentDtoLikes = new HashMap<>();
        for (Comment comment: comments) {
            Long voteCount = commentVoteRepository.countByCommentId(comment.getId());
            CommentDto commentDto = commentService.commentToCommentDto(comment);
            commentDtoLikes.put(commentDto.getId(), voteCount);
        }
        return commentDtoLikes;
    }

    public CommentVoteDto articleVoteToArticleVoteDto(CommentVote commentVote) {
        return CommentVoteDto.builder()
                .id(commentVote.getId())
                .siteUserDto(userService.siteUserToSiteUserForm(commentVote.getUser()))
                .commentDto(commentService.commentToCommentDto(commentVote.getComment()))
                .build();
    }

    private boolean isDuplicateRecommendationVoter(final Long commentId, final Long userId) {

        Optional<CommentVote> commentVote = commentVoteRepository.findByCommentIdAndUserId(commentId, userId);
        return commentVote.isPresent();
    }


}
