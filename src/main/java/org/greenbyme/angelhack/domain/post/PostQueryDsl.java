package org.greenbyme.angelhack.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostQueryDsl {

    List<Post> findAllByUserId(Long userId);

    Post findByPersonalMissionId(Long personalMissionId);

    Optional<Post> findByIdWithFetch(Long postId);

    List<Post> findAllByPersonalMissionId(Long personalMissionId);

    void deleteByUserId(Long userId);
}
