package org.greenbyme.angelhack.domain.personalmission;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.greenbyme.angelhack.domain.mission.QMission;
import org.greenbyme.angelhack.domain.post.QPost;
import org.greenbyme.angelhack.domain.user.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.greenbyme.angelhack.domain.personalmission.QPersonalMission.personalMission;
import static org.greenbyme.angelhack.domain.post.QPost.post;

public class PersonalMissionQueryDslImpl implements PersonalMissionQueryDsl {

    private final JPAQueryFactory queryFactory;

    public PersonalMissionQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PersonalMission> findAllByUserIdPageable(Long userId, Pageable pageable) {
        List<PersonalMission> content = queryFactory
                .selectFrom(personalMission)
                .where(userIdEq(userId))
                .orderBy(personalMission.personalMissionStatus.desc(), personalMission.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<PersonalMission> count = queryFactory
                .selectFrom(personalMission)
                .where(userIdEq(userId));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }

    @Override
    public List<PersonalMission> findAllByUserIdFetch(Long userId) {
        return queryFactory
                .selectFrom(personalMission)
                .leftJoin(personalMission.mission, QMission.mission).fetchJoin()
                .leftJoin(personalMission.user, QUser.user).fetchJoin()
                .where(userIdEq(userId))
                .fetch();
    }

    @Override
    public Long countHowManyPeopleInMission(Long missionId) {
        return queryFactory
                .selectFrom(personalMission)
                .where(missionIdEq(missionId))
                .fetchCount();
    }

    @Override
    public void deleteByUserId(Long userId) {
        queryFactory
                .delete(personalMission)
                .where(userIdEq(userId))
                .execute();
    }

    @Override
    public Page<PersonalMission> findAllByMissionId(Long missionId, Pageable pageable) {
        List<PersonalMission> content = queryFactory
                .selectFrom(personalMission)
                .where(missionIdEq(missionId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<PersonalMission> countQuery = queryFactory
                .selectFrom(personalMission)
                .where(missionIdEq(missionId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public Optional<PersonalMission> findDetailsById(Long personalMissionId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(personalMission)
                .leftJoin(personalMission.user, QUser.user).fetchJoin()
                .leftJoin(personalMission.mission, QMission.mission).fetchJoin()
                .where(personalMissionIdEq(personalMissionId))
                .fetchOne());
    }

    @Override
    public Long findProgressByMissionId(Long missionId) {
        return queryFactory.selectFrom(personalMission)
                .where(missionIdEq(missionId), personalMissionStatusEq(PersonalMissionStatus.IN_PROGRESS))
                .fetchCount();
    }

    @Override
    public List<PersonalMission> findInProgressPersonalMissionsByUserId(Long userId) {
        return queryFactory
                .selectFrom(personalMission)
                .leftJoin(personalMission.mission, QMission.mission).fetchJoin()
                .where(userIdEq(userId), personalMissionStatusEq(PersonalMissionStatus.IN_PROGRESS))
                .fetch();
    }

    @Override
    public List<PersonalMission> findInProgressPersonalMissionByUserIdWithCertification(Long userId) {
        return queryFactory
                .selectFrom(personalMission)
                .leftJoin(personalMission.posts, post).fetchJoin()
                .where(userIdEq(userId), personalMission.personalMissionStatus.eq(PersonalMissionStatus.IN_PROGRESS),
                        post.createdDate.dayOfYear().eq(LocalDateTime.now().getDayOfYear()))
                .fetch();
    }

    @Override
    public Page<PersonalMission> findInProgressPersonalMissionsByUserId(Long userId, Pageable pageable) {
        List<PersonalMission> content = queryFactory
                .selectFrom(personalMission)
                .where(userIdEq(userId),
                        personalMissionStatusEq(PersonalMissionStatus.IN_PROGRESS)
                )
                .leftJoin(personalMission.mission, QMission.mission).fetchJoin()
                .orderBy(personalMission.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<PersonalMission> countQuery = queryFactory
                .selectFrom(personalMission)
                .where(userIdEq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? personalMission.user.id.eq(userId) : null;
    }

    private BooleanExpression personalMissionStatusEq(PersonalMissionStatus personalMissionStatus) {
        return !ObjectUtils.isEmpty(personalMissionStatus) ? personalMission.personalMissionStatus.eq(personalMissionStatus) : null;
    }

    private BooleanExpression missionIdEq(Long missionId) {
        return missionId != null ? personalMission.mission.id.eq(missionId) : null;
    }

    private BooleanExpression personalMissionIdEq(Long personalMissionId) {
        return personalMissionId != null ? personalMission.id.eq(personalMissionId) : null;
    }
}
