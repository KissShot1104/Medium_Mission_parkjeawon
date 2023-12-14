package com.mysite.medium.comment_vote.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentVote is a Querydsl query type for CommentVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentVote extends EntityPathBase<CommentVote> {

    private static final long serialVersionUID = -1201473769L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentVote commentVote = new QCommentVote("commentVote");

    public final com.mysite.medium.comment.entity.QComment comment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.mysite.medium.user.entity.QSiteUser user;

    public QCommentVote(String variable) {
        this(CommentVote.class, forVariable(variable), INITS);
    }

    public QCommentVote(Path<? extends CommentVote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentVote(PathMetadata metadata, PathInits inits) {
        this(CommentVote.class, metadata, inits);
    }

    public QCommentVote(Class<? extends CommentVote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new com.mysite.medium.comment.entity.QComment(forProperty("comment"), inits.get("comment")) : null;
        this.user = inits.isInitialized("user") ? new com.mysite.medium.user.entity.QSiteUser(forProperty("user")) : null;
    }

}

