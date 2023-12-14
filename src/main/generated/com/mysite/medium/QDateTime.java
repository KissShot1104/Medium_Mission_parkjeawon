package com.mysite.medium;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDateTime is a Querydsl query type for DateTime
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QDateTime extends EntityPathBase<DateTime> {

    private static final long serialVersionUID = -1887748058L;

    public static final QDateTime dateTime = new QDateTime("dateTime");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public QDateTime(String variable) {
        super(DateTime.class, forVariable(variable));
    }

    public QDateTime(Path<? extends DateTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDateTime(PathMetadata metadata) {
        super(DateTime.class, metadata);
    }

}

