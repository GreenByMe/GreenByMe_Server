package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalMissionPageDto {

    private List<PersonalMissionByPageDto> personalMissionList;
    private List<PopularMissionResponseDto> popularMissionResponseDtoList;

    public PersonalMissionPageDto(List<PersonalMissionByPageDto> personalMissionList,
                                  List<PopularMissionResponseDto> popularMissionResponseDtoList) {
        this.personalMissionList = personalMissionList;
        this.popularMissionResponseDtoList = popularMissionResponseDtoList;
    }
}
