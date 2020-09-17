package org.greenbyme.angelhack.domain.personalmission;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.greenbyme.angelhack.domain.mission.QMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static org.greenbyme.angelhack.domain.personalmission.QPersonalMission.*;

public class PersonalMissionQueryDslImpl implements PersonalMissionQueryDsl {

    private final JPAQueryFactory queryFactory;

    public PersonalMissionQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PersonalMission> findInProgressPersonalMissionsByUserId(Long id, Pageable pageable) {
        List<PersonalMission> content = queryFactory
                .selectFrom(personalMission)
                .where(userIdEq(id),
                        personalMissionStatusEq(PersonalMissionStatus.IN_PROGRESS)
                )
                .leftJoin(personalMission.mission, QMission.mission).fetchJoin()
                .orderBy(personalMission.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<PersonalMission> countQuery = queryFactory
                .selectFrom(personalMission)
                .where(userIdEq(id));


        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchCount());
    }

    private BooleanExpression userIdEq(Long userId){
        return userId != null? personalMission.user.id.eq(userId) : null;
    }

    private BooleanExpression personalMissionStatusEq(PersonalMissionStatus personalMissionStatus){
        return !ObjectUtils.isEmpty(personalMissionStatus) ? personalMission.personalMissionStatus.eq(personalMissionStatus) : null;
    }
}
