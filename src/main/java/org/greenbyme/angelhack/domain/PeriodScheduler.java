package org.greenbyme.angelhack.domain;

import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.service.MissionInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class PeriodScheduler {

    @Autowired
    MissionInfoService missionInfoService;

    private static final Logger logger = LoggerFactory.getLogger(PeriodScheduler.class);

    @Scheduled(fixedDelay = 1000*60)
    public void alert(){
        logger.info("현재 시간 : {}", new Date());
        missionInfoService.changeRemainPeriod();

    }
}
