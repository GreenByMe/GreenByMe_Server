package org.greenbyme.angelhack.domain.post;

import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByPersonalMission(PersonalMission personalMission);

    List<Post> findAllByUser(User user);

    Page<Post> findAllByUser(User user, Pageable pageable);

    List<Post> findAllByUserAndPersonalMission(User user, PersonalMission personalMission);

    List<Post> findAllByPersonalMission(PersonalMission personalMission);
}
