package org.greenbyme.angelhack.domain.personalmission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonalMissionQueryDsl {

    Page<PersonalMission> findInProgressPersonalMissionsByUserId(Long id , Pageable pageable);

    Long findProgressByMissionId(Long missionId);

    Optional<PersonalMission> findDetailsById(Long id);

}
