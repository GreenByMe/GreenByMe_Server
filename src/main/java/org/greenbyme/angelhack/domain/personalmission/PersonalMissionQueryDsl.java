package org.greenbyme.angelhack.domain.personalmission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonalMissionQueryDsl {

    Page<PersonalMission> findInProgressPersonalMissionsByUserId(Long userId , Pageable pageable);

    Long findProgressByMissionId(Long missionId);

    Optional<PersonalMission> findDetailsById(Long personalMissionId);

    Page<PersonalMission> findAllByMissionId(Long missionId, Pageable pageable);

    List<PersonalMission> findPersonalMissionByUserIdAndWhereInProgress(Long userId);

    List<PersonalMission> findAllByUserIdFetch(Long userId);

    Long countHowManyPeopleInMission(Long missionId);
}
