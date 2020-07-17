package org.greenbyme.angelhack.domain.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.baseEntity.BaseEntity;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.post.PostUpdateRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missioninfo_id")
    private MissionInfo missionInfo;

    private String picture;

    private String title;
    private String text;

    private Integer thumbsUp;
    private Boolean open;

    @Builder
    public Post(User user, String text, String title, String picture, Boolean open) {
        setUser(user);
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.thumbsUp = 0;
        this.open = open;
    }

    @Builder
    public Post(User user, MissionInfo missionInfo, String text, String title, String picture, Boolean open) {
        setUser(user);
        this.missionInfo = missionInfo;
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.thumbsUp = 0;
        this.open = open;
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
        this.picture = requestDto.getPicture();
        this.open = requestDto.getOpen();
        this.title = requestDto.getTitle();
    }

    public void thumbsUp() {
        this.thumbsUp += 1;
    }
}
