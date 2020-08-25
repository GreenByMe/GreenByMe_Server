package org.greenbyme.angelhack.domain.personalmission;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemainPeriod {

    private Long remainDay;
    private Long remainHour;
    private Long remainMin;

    public RemainPeriod(Long remainDay, Long remainHour, Long remainMin){
        this.remainDay = remainDay;
        this.remainHour = remainHour;
        this.remainMin = remainMin;
    }
}
