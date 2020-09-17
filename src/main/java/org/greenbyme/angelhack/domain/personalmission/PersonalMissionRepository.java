package org.greenbyme.angelhack.domain.personalmission;

import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonalMissionRepository extends JpaRepository<PersonalMission, Long>, PersonalMissionJPQL, PersonalMissionQueryDsl {

    List<PersonalMission> findAllByMission(Mission mission);

    List<PersonalMission> findByMission(Mission mission);

    Page<PersonalMission> findAllByMission(Mission mission, Pageable pageable);

    List<PersonalMission> findAllByUser(User user);

    @Query("select p from PersonalMission p where p.personalMissionStatus = 'IN_PROGRESS'")
    Page<PersonalMission> findAllByUser(User user, Pageable pageable);

    List<PersonalMission> findPersonalMissionByUserIdAndMissionId(Long userId, Long missionId);

    List<PersonalMission> findByPersonalMissionStatusEquals(PersonalMissionStatus progress);
}
