package org.greenbyme.angelhack.domain.post;

import org.greenbyme.angelhack.domain.personalmission.PersonalMission;

import java.util.List;

public interface PostQueryDsl {

    List<Post> findAllByUserId(Long userId);

    Post findByPersonalMissionId(Long personalMissionId);
}
