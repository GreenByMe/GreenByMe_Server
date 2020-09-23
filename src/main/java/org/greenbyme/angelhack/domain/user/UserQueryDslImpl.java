package org.greenbyme.angelhack.domain.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.greenbyme.angelhack.domain.personalmission.QPersonalMission;
import org.greenbyme.angelhack.domain.postlike.QPostLike;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.greenbyme.angelhack.domain.user.QUser.*;


public class UserQueryDslImpl implements UserQueryDsl {

    private final JPAQueryFactory queryFactory;

    public UserQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<User> findByIdFetch(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(user)
                .leftJoin(user.personalMissionList, QPersonalMission.personalMission).fetchJoin()
                .leftJoin(user.postLikes, QPostLike.postLike)
                .where(user.id.eq(id))
                .fetchOne());
    }
}
