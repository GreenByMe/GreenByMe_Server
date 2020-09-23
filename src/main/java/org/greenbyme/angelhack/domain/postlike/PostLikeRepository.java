package org.greenbyme.angelhack.domain.postlike;

import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeQueryDsl {

    Optional<PostLike> findByUserAndPost(User user, Post post);

    List<PostLike> findAllByPost(Post post);
}
