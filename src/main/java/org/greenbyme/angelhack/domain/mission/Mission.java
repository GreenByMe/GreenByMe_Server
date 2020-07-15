package org.greenbyme.angelhack.domain.mission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.baseEntity.BaseTimeEntity;
import org.greenbyme.angelhack.domain.user.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Mission extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String subject;
    private String description;

    @Builder
    public Mission(Category category, String subject, String description){
        this.category = category;
        this.subject = subject;
        this.description = description;
    }
}

