package org.greenbyme.angelhack.domain.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Mission {

    @Id
    @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    private String category;
    private String subject;
    private String description;
}

