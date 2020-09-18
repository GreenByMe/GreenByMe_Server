package org.greenbyme.angelhack.domain.personalmission;

import java.util.List;
import java.util.Optional;

public interface PersonalMissionJPQL {

    List<PersonalMission> findPersonalMissionByUserIdAndWhereInProgress(Long userId);
}
