package org.greenbyme.angelhack.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.baseEntity.BaseEntity;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.postlike.PostLike;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String nickname;
    private String photo;
    private double expectTree;
    private double expectCo2;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    private String platformId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PersonalMission> personalMissionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostLike> postLikes = new ArrayList<>();

    @Builder
    public User(String name, String email, String password, String nickname, String photo, List<String> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.roles = roles;
        this.photo = photo;
        this.expectCo2 = 0;
        this.expectTree = 0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.id.toString();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changePassword(String password) {
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

    public void changeNickName(String nickName) {
        this.nickname = nickName;
    }

    public void deleteLike(PostLike postLike) {
        this.postLikes.remove(postLike);
    }

    public void addLikes(PostLike postLike) {
        this.postLikes.add(postLike);
    }
}
