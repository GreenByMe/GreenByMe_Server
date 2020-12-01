package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CertPageDto {

    private Long userId;
    private List<ProgressPersonalMissionDto> personalMissions;

    public CertPageDto(Long userId, List<ProgressPersonalMissionDto> personalMissions) {
        this.userId = userId;
        this.personalMissions = personalMissions;
    }
}
