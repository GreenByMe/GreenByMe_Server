package org.greenbyme.angelhack.domain.missionInfo;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class MissionInfoJPQLImpl implements MissionInfoJPQL{

    private final EntityManager em;


}
