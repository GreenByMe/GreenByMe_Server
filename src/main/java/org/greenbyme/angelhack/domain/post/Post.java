package org.greenbyme.angelhack.domain.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.baseEntity.BaseEntity;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.postlike.PostLike;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.post.PostUpdateRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_mission_id")
    private PersonalMission personalMission;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLikes = new ArrayList<>();

    private String picture;

    private String title;
    private String text;
    private Boolean open;

    @Builder
    public Post(User user, String text, String title, String picture, Boolean open) {
        setUser(user);
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.open = open;
    }

    @Builder
    public Post(User user, PersonalMission personalMission, String text, String title, String picture, Boolean open) {
        setUser(user);
        setPersonalMission(personalMission);
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.open = open;
    }

    private void setPersonalMission(PersonalMission personalMission){
        this.personalMission = personalMission;
        personalMission.getPosts().add(this);
    }

    private void setUser(User user) {
        this.user = user;
        user.getPostList().add(this);
    }

    public boolean isOpen() {
        return this.open;
    }

    public void update(PostUpdateRequestDto requestDto) {
        this.text = requestDto.getText();
        this.open = requestDto.getOpen();
        this.title = requestDto.getTitle();
    }

    public void deleteLike(PostLike postLike) {
        this.postLikes.remove(postLike);
    }

    public void addLikes(PostLike postLike) {
        this.postLikes.add(postLike);
    }
}
