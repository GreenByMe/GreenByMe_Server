package org.greenbyme.angelhack.domain.postlike;

import org.greenbyme.angelhack.domain.post.Post;

import java.util.List;

public interface PostLikeQueryDsl {

    List<PostLike> findAllByPostId(Long postId);
}
