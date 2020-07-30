package org.greenbyme.angelhack.domain.missionInfo;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MissionInfoJPQLImpl implements MissionInfoJPQL{

    private final EntityManager em;

    @Override
    public Optional<MissionInfo> findDetailsById(Long id) {
        return em.createQuery("select mi from MissionInfo mi" +
                " join fetch mi.mission m" +
                " join fetch mi.user u" +
                " where mi.id =: id", MissionInfo.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Long findProgressByMissionId(Long missionId) {
        return em.createQuery("select count(mi) from MissionInfo  mi" +
                " join mi.mission m" +
                " where m.id =:id and mi.missionInfoStatus =:missionInfoStatus ", Long.class)
                .setParameter("id", missionId)
                .setParameter("missionInfoStatus", MissionInfoStatus.IN_PROGRESS)
                .getSingleResult();
    }

    @Override
    public List<MissionInfo> findMissionInfoByUserIdAndWhereInProgress(Long userId) {
        return em.createQuery("select mi from MissionInfo mi"+
                " join fetch mi.user u"+
                " where u.id =: id and mi.missionInfoStatus =: missionInfoStatus order by mi.id", MissionInfo.class)
                .setParameter("id", userId)
                .setParameter("missionInfoStatus", MissionInfoStatus.IN_PROGRESS)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
