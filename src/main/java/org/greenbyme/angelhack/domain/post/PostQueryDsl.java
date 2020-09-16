package org.greenbyme.angelhack.domain.post;

import java.util.List;

public interface PostQueryDsl {

    List<Post> findAllByUserId(Long userId);
}
