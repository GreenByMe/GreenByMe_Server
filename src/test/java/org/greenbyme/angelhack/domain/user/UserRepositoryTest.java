package org.greenbyme.angelhack.domain.user;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;
    @PersistenceContext EntityManager em;

    @Test
    public void userSaveTest(){
        User userA = userRepository.save(User.builder().name("userA").build());
        System.out.println("userA = " + userA.getName());
    }

    @DisplayName("create후 update에서 modifiedDate 변경 확인")
    @Ignore
    @Test
    public void userModifiedTest() throws InterruptedException {
        User userA = userRepository.save(User.builder().name("userA").build());
        Thread.sleep(5000);

        userA.changeName("userB");

        em.flush();
        em.clear();

        User changedUser = userRepository.findById(userA.getId()).orElseThrow(() -> new IllegalStateException("없는 사용자"));

        assertThat(userA.getCreatedDate()).isEqualTo(changedUser.getLastModifiedDate());

    }

}