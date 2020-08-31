package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.service.dto.personalmission.FinishedResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.InProgressResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalMissionPageDto {

    private List<InProgressResponseDto> progressResponseDtoList;
    private List<FinishedResponseDto> finishedResponseDtoList;
    private List<PopularMissionResponseDto> popularMissionResponseDtoList;

    public PersonalMissionPageDto(List<InProgressResponseDto> progressResponseDtoList,
                                  List<FinishedResponseDto> finishedResponseDtoList,
                                  List<PopularMissionResponseDto> popularMissionResponseDtoList) {
        this.progressResponseDtoList = progressResponseDtoList;
        this.finishedResponseDtoList = finishedResponseDtoList;
        this.popularMissionResponseDtoList = popularMissionResponseDtoList;
    }
}
