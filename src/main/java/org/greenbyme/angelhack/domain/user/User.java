package org.greenbyme.angelhack.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.baseEntity.BaseEntity;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String nickname;
    private String photo;
    private double expectTree;
    private double expectCo2;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MissionInfo> missionInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @Builder
    public User(String name, String email, String password, String nickname, String photo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.photo = photo;
        this.expectCo2 = 0;
        this.expectTree = 0;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changePassword(String password){
       this.password = password;
    }

    public void addExpectCo2(double expectCo2){
       this.expectCo2 = expectCo2;
       this.expectTree = expectCo2/3.17;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changePhoto(String photo) {
        this.photo = photo;
    }
}