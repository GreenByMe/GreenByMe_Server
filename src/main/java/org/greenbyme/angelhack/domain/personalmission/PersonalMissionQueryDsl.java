package org.greenbyme.angelhack.domain.personalmission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonalMissionQueryDsl {

    Page<PersonalMission> findInProgressPersonalMissionsByUserId(Long id , Pageable pageable);

    Long findProgressByMissionId(Long missionId);
}
