package org.greenbyme.angelhack.domain.postlike;

import java.util.List;

public interface PostLikeQueryDsl {

    List<PostLike> findAllByPostId(Long postId);

    void deleteByPostIds(List<Long> postIds);
}
