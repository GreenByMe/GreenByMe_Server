package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveRequestDto {

    @NotNull @Positive
    private Long personalMission_id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotNull
    private Boolean open;

    public PostSaveRequestDto(Long personalMission_id, String title, String text, Boolean open) {
        this.personalMission_id = personalMission_id;
        this.title = title;
        this.text = text;
        this.open = open;
    }
}
