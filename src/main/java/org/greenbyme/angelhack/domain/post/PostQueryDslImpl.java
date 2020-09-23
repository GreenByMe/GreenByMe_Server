package org.greenbyme.angelhack.domain.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.greenbyme.angelhack.domain.personalmission.QPersonalMission.personalMission;
import static org.greenbyme.angelhack.domain.post.QPost.post;
import static org.greenbyme.angelhack.domain.postlike.QPostLike.postLike;
import static org.greenbyme.angelhack.domain.user.QUser.user;

public class PostQueryDslImpl implements PostQueryDsl {

    private final JPAQueryFactory queryFactory;

    public PostQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findAllByPersonalMissionId(Long personalMissionId) {
        return queryFactory
                .selectFrom(post)
                .where(personMissionIdEq(personalMissionId))
                .leftJoin(post.postLikes, postLike).fetchJoin()
                .leftJoin(post.user, user).fetchJoin()
                .fetch();
    }


    @Override
    public Optional<Post> findByIdWithFetch(Long postId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(post)
                .leftJoin(post.postLikes, postLike).fetchJoin()
                .leftJoin(post.user, user).fetchJoin()
                .where(postIdEq(postId))
                .fetchOne());
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
        return id != null ? post.personalMission.id.eq(id)  : null;
    }

    private BooleanExpression postIdEq(Long id) {
        return id != null ? post.id.eq(id) : null;
    }
}
