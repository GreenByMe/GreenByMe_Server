package org.greenbyme.angelhack.domain.personalmission;

import org.greenbyme.angelhack.domain.mission.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonalMissionQueryDsl {

    Page<PersonalMission> findInProgressPersonalMissionsByUserId(Long id , Pageable pageable);

    Long findProgressByMissionId(Long missionId);

    Optional<PersonalMission> findDetailsById(Long id);

    Page<PersonalMission> findAllByMissionId(Long id, Pageable pageable);

    List<PersonalMission> findPersonalMissionByUserIdAndWhereInProgress(Long userId);
}
