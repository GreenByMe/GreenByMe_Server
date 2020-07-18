package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CertPageDto {

    private Long userId;
    private List<ProgressMissionInfoDto> missionList;

    public CertPageDto(Long userId, List<MissionInfo> missionInfos) {
        this.userId = userId;
        this.missionList = missionInfos.stream()
                .map(ProgressMissionInfoDto::new)
                .collect(Collectors.toList());
    }
}
