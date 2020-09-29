package org.greenbyme.angelhack.domain.posttag;

import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    Optional<PostTag> findByPostAndTag(Post post, Tag tag);

    List<PostTag> findAllByTag(Tag tag);
}
