package com.mysite.medium.article_vote.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleVote is a Querydsl query type for ArticleVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleVote extends EntityPathBase<ArticleVote> {

    private static final long serialVersionUID = 1824324549L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleVote articleVote = new QArticleVote("articleVote");

    public final com.mysite.medium.article.entity.QArticle article;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.mysite.medium.user.entity.QSiteUser user;

    public QArticleVote(String variable) {
        this(ArticleVote.class, forVariable(variable), INITS);
    }

    public QArticleVote(Path<? extends ArticleVote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleVote(PathMetadata metadata, PathInits inits) {
        this(ArticleVote.class, metadata, inits);
    }

    public QArticleVote(Class<? extends ArticleVote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new com.mysite.medium.article.entity.QArticle(forProperty("article"), inits.get("article")) : null;
        this.user = inits.isInitialized("user") ? new com.mysite.medium.user.entity.QSiteUser(forProperty("user")) : null;
    }

}

