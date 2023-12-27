package com.mysite.medium.comment_vote.service;


import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.dto.CommentMapper;
import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.comment.repository.CommentRepository;
import com.mysite.medium.comment_vote.entity.CommentVote;
import com.mysite.medium.comment_vote.repository.CommentVoteRepository;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.repository.UserRepository;
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

    private final CommentMapper commentMapper;


    @Transactional
    public void toggleCommentVote(final Long commentId, final String username) {
        final Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new EntityNotFoundException("Comment Entity Not Found");
        }

        final Optional<SiteUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User Entity Not Found");
        }

        final Optional<CommentVote> commentVote = commentVoteRepository.findByCommentIdAndUserId(commentId,
                user.get().getId());
        if (commentVote.isEmpty()) {
            createCommentVote(comment.get(), user.get());
        } else {
            deleteCommentVoteAllByCommentId(commentId);
        }
    }

    public void createCommentVote(final Comment comment, final SiteUser user) {
        final CommentVote commentVote = CommentVote.builder()
                .article(comment.getArticle())
                .comment(comment)
                .user(user)
                .build();

        commentVoteRepository.save(commentVote);
    }

    @Transactional
    public void deleteCommentVoteAllByCommentId(final Long commentId) {
        commentVoteRepository.deleteCommentVoteAllByCommentId(commentId);
    }

    @Transactional
    public void deleteCommentVoteAllByArticleId(final Long articleId) {
        commentVoteRepository.deleteAllByArticleId(articleId);
    }


    public Map<Long, Long> getCommentLikesForArticle(final Long articleId) {//article 생겼으니 다시 생각해보자
        final List<Comment> comments = commentRepository.findAllByArticleId(articleId);
        final Map<Long, Long> commentDtoLikes = new HashMap<>();
        for (Comment comment : comments) {
            final Long voteCount = commentVoteRepository.countByCommentId(comment.getId());
            final CommentDto commentDto = commentMapper.commentToCommentDto(comment);
            commentDtoLikes.put(commentDto.getId(), voteCount);
        }
        return commentDtoLikes;
    }


}
