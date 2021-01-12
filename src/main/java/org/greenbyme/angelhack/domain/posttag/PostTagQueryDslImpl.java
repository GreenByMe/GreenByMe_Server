package org.greenbyme.angelhack.domain.posttag;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static org.greenbyme.angelhack.domain.posttag.QPostTag.*;

public class PostTagQueryDslImpl implements PostTagQueryDsl {

    private final JPAQueryFactory queryFactory;

    public PostTagQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteByPostIds(List<Long> postIds) {
        queryFactory.delete(postTag)
                .where(postTag.post.id.in(postIds))
                .execute();
    }
}
