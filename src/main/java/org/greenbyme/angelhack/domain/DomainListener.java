package org.greenbyme.angelhack.domain;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * @PostLoad: 해당 엔티티를 새로 불러오거나 refresh 한 이후.
 * @PrePersist: 해당 엔티티를 저장하기 이전
 * @PostPersist: 해당 엔티티를 저장한 이후
 * @PreUpdate: 해당 엔티티를 업데이트 하기 이전
 * @PostUpdate: 해당 엔티티를 업데이트 한 이후
 * @PreRemove: 해당 엔티티를 삭제하기 이전
 * @PostRemove: 해당 엔티티를 삭제한 이후
 */
@Slf4j
public class DomainListener {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(DomainListener.class);

    @PostLoad
    public void postLoad(Object obj) {
        logger.info("post load: {}", obj);
    }

    @PrePersist
    public void prePersist(Object obj) {
        logger.info("pre persist: {}", obj);
    }

    @PostPersist
    public void postPersist(Object obj) {
        logger.info("post persist: {}", obj);
    }

    @PreUpdate
    public void preUpdate(Object obj) {
        logger.info("pre update: {}", obj);
    }

    @PostUpdate
    public void postUpdate(Object obj) {
        logger.info("post update: {}", obj);
    }

    @PreRemove
    public void preRemove(Object obj) {
        logger.info("pre remove: {}", obj);
    }

    @PostRemove
    public void postRemove(Object obj) {
        logger.info("post remove: {}", obj);
    }
}
