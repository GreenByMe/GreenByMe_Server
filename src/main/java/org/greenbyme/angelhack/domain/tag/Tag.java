package org.greenbyme.angelhack.domain.tag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbyme.angelhack.domain.DomainListener;
import org.greenbyme.angelhack.domain.posttag.PostTag;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.TagException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(DomainListener.class)
@ToString(of = {"id", "tagName"})
public class Tag {

    private static final String SPACE = " ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<PostTag> postTagList = new ArrayList<>();

    public Tag(String tagName) {
        this.tagName = tagName;
        validateTagName(tagName);
    }

    private void validateTagName(String tagName) {
        if (tagName.contains(SPACE)) {
            throw new TagException(ErrorCode.INVALID_TAG_NAME);
        }
        if (tagName.isEmpty()) {
            throw new TagException(ErrorCode.EMPTY_TAG_NAME);
        }
    }
}
