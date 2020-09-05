package org.greenbyme.angelhack.domain;

import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.PersonalMissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class PeriodScheduler {

    @Autowired
    PersonalMissionService personalMissionService;

    private static final Logger logger = LoggerFactory.getLogger(PeriodScheduler.class);

    @Scheduled(fixedDelay = 1000*60)
    public void alert(){
        //logger.info("유저 RemainPeriod 변경 완료 / 현재 시간 : {}", new Date());
        personalMissionService.changeRemainPeriod();
    }
}
