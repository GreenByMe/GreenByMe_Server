package org.greenbyme.angelhack.domain.personalmission;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PersonalMissionJPQLImpl implements PersonalMissionJPQL {

    private final EntityManager em;

    @Override
    public List<PersonalMission> findPersonalMissionByUserIdAndWhereInProgress(Long userId) {
        return em.createQuery("select mi from PersonalMission mi"+
                " join fetch mi.user u"+
                " where u.id =: id and mi.personalMissionStatus =: personalMissionStatus order by mi.id", PersonalMission.class)
                .setParameter("id", userId)
                .setParameter("personalMissionStatus", PersonalMissionStatus.IN_PROGRESS)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
