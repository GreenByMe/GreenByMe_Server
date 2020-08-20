package org.greenbyme.angelhack.domain.post;

import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByPersonalMission(PersonalMission personalMission);
    List<Post> findAllByUser(User user);
    List<Post> findAllByUserAndPersonalMission(User user, PersonalMission personalMission);
}
