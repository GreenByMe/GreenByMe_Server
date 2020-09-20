package org.greenbyme.angelhack.domain.post;

import org.greenbyme.angelhack.domain.personalmission.PersonalMission;

import java.util.List;
import java.util.Optional;

public interface PostQueryDsl {

    List<Post> findAllByUserId(Long userId);

    Post findByPersonalMissionId(Long personalMissionId);

    Optional<Post> findByIdWithFetch(Long postId);
}
