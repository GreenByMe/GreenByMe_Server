package org.greenbyme.angelhack.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    public void userSaveTest(){
        User userA = userRepository.save(User.builder().name("userA").build());
        System.out.println("userA = " + userA.getName());
    }

}