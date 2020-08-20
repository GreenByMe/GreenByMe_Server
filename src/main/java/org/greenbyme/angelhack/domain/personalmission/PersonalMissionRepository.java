package org.greenbyme.angelhack.domain.personalmission;

import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalMissionRepository extends JpaRepository<PersonalMission, Long>, PersonalMissionJPQL {

    List<PersonalMission> findAllByMission(Mission mission);

    List<PersonalMission> findByMission(Mission mission);

    List<PersonalMission> findAllByUser(User user);

    List<PersonalMission> findPersonalMissionByUserIdAndMissionId(Long userId, Long missionId);

    List<PersonalMission> findByPersonalMissionStatusEquals(PersonalMissionStatus progress);
}
