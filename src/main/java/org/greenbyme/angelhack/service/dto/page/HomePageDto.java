package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionHomePageDto;
import org.greenbyme.angelhack.service.dto.user.UserHomePageDetailDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomePageDto {
    UserHomePageDetailDto userHomePageDetailDto;
    List<PersonalMissionHomePageDto> personalMissionHomePageDtos;
    List<PopularMissionHomePageResponseDto> popularMissionHomePageResponseDtos;

    public HomePageDto(UserHomePageDetailDto userHomePageDetailDto, List<PersonalMissionHomePageDto> personalMissionHomePageDtos, List<PopularMissionHomePageResponseDto> popularMissionHomePageResponseDtos) {
        this.userHomePageDetailDto = userHomePageDetailDto;
        this.personalMissionHomePageDtos = personalMissionHomePageDtos;
        this.popularMissionHomePageResponseDtos = popularMissionHomePageResponseDtos;
    }
}
