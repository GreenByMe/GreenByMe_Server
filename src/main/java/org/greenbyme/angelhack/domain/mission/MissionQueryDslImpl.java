package org.greenbyme.angelhack.domain.mission;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.service.dto.mission.MissionFindAllByCategoryAndDayCategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.greenbyme.angelhack.domain.mission.QMission.mission;

public class MissionQueryDslImpl implements MissionQueryDsl {

    private final JPAQueryFactory queryFactory;

    public MissionQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MissionFindAllByCategoryAndDayCategoryResponseDto> findAllByCategoryAndDayCategory(Category category, DayCategory dayCategory, Pageable pageable) {
        List<MissionFindAllByCategoryAndDayCategoryResponseDto> content = queryFactory
                .select(Projections.constructor(MissionFindAllByCategoryAndDayCategoryResponseDto.class,
                        mission))
                .from(mission)
                .where(categoryEq(category),
                        dayCategoryEq(dayCategory))
                .orderBy(mission.category.asc(), mission.dayCategory.asc(), mission.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Mission> countQuery = queryFactory
                .selectFrom(mission)
                .where(categoryEq(category),
                        dayCategoryEq(dayCategory));
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }

    private BooleanExpression categoryEq(Category category) {
        if (ObjectUtils.isEmpty(category)) {
            return null;
        }
        if (category.equals(Category.ALL)) {
            return null;
        }
        return mission.category.eq(category);
    }

    private BooleanExpression dayCategoryEq(DayCategory dayCategory) {
        if (ObjectUtils.isEmpty(dayCategory)) {
            return null;
        }
        if (dayCategory.equals(DayCategory.ALL)) {
            return null;
        }
        return mission.dayCategory.eq(dayCategory);
    }
}
