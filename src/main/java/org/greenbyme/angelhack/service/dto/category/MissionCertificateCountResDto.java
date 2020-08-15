package org.greenbyme.angelhack.service.dto.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;

import java.util.HashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionCertificateCountResDto {

    private HashMap<MissionCertificateCount, Integer> certificateCounts;

    public MissionCertificateCountResDto(HashMap<MissionCertificateCount, Integer> certificateCounts) {
        this.certificateCounts = certificateCounts;
    }
}
