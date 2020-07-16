package org.greenbyme.angelhack.domain.post;

import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByMissionInfo(MissionInfo missionInfo);
    List<Post> findAllByUser(User user);
}