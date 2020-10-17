package org.greenbyme.angelhack.domain;

import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.PersonalMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PeriodScheduler {

    @Autowired
    PersonalMissionService personalMissionService;

    @Scheduled(cron = "0 0 0 * * *")
    public void alert(){
        log.info("00:00 모든 유저 개인 미션 남은기간 변경 시작");
        personalMissionService.changeRemainPeriod();
        log.info("모든 유저 개인 미션 남은기간 변경 완료");
    }
}
