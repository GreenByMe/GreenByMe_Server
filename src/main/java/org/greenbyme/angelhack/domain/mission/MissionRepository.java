package org.greenbyme.angelhack.domain.mission;

import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository  extends JpaRepository<Mission, Long>, MissionJPQL {

    Page<Mission> findAllByCategoryAndDayCategory(Category category, DayCategory dayCategory, Pageable pageable);

    Page<Mission> findAllByCategory(Category category, Pageable pageable);
}
