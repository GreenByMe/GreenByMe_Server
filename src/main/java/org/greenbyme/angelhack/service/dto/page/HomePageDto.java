package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomePageDto {

    private UserHomePageDetailDto userHomePageDetailDto;
    private List<PersonalMissionByPageDto> personalMissionHomePageDtos;
    private List<PopularMissionHomePageResponseDto> popularMissionHomePageResponseDtos;

    public HomePageDto(UserHomePageDetailDto userHomePageDetailDto, List<PersonalMissionByPageDto> personalMissionHomePageDtos, List<PopularMissionHomePageResponseDto> popularMissionHomePageResponseDtos) {
        this.userHomePageDetailDto = userHomePageDetailDto;
        this.personalMissionHomePageDtos = personalMissionHomePageDtos;
        this.popularMissionHomePageResponseDtos = popularMissionHomePageResponseDtos;
    }
}
