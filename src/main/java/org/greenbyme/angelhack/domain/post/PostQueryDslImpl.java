package org.greenbyme.angelhack.domain.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.postlike.QPostLike;

import javax.persistence.EntityManager;
import java.util.List;

import static org.greenbyme.angelhack.domain.personalmission.QPersonalMission.*;
import static org.greenbyme.angelhack.domain.post.QPost.*;
import static org.greenbyme.angelhack.domain.postlike.QPostLike.*;
import static org.greenbyme.angelhack.domain.user.QUser.*;

public class PostQueryDslImpl implements PostQueryDsl {

    private final JPAQueryFactory queryFactory;

    public PostQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Post findByPersonalMissionId(Long personalMissionId) {
        return queryFactory
                .selectFrom(post)
                .where(personMissionIdEq(personalMissionId))
                .leftJoin(post.postLikes, postLike).fetchJoin()
                .leftJoin(post.user, user).fetchJoin()
                .orderBy(post.id.desc())
                .fetchFirst();
    }

    @Override
    public List<Post> findAllByUserId(Long userId) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.personalMission, personalMission).fetchJoin()
                .leftJoin(post.postLikes, postLike).fetchJoin()
                .where(post.user.id.eq(userId))
                .fetch();
    }

    private BooleanExpression personMissionIdEq(Long id) {
        return id != null ? post.personalMission.id.eq(id) : null;
    }
}
