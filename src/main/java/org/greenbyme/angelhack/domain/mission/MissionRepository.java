package org.greenbyme.angelhack.domain.mission;

import org.greenbyme.angelhack.domain.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository  extends JpaRepository<Mission, Long>, MissionQueryDsl {

    Page<Mission> findAllByCategory(Category category, Pageable pageable);

    Page<Mission> findAll( Pageable pageable);
}
