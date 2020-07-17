package org.greenbyme.angelhack.domain.mission;


import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MissionJPQLImpl implements MissionJPQL {

    private final EntityManager em;
}
