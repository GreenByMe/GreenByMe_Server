package org.greenbyme.angelhack.domain.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public List<Post> findAllByUserId(Long userId) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.personalMission, personalMission).fetchJoin()
                .leftJoin(post.postLikes, postLike).fetchJoin()
                .where(post.user.id.eq(userId))
                .fetch();
    }
}
