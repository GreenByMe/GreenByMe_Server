package org.greenbyme.angelhack.domain.user;

import java.util.Optional;

public interface UserQueryDsl {

    Optional<User> findByIdFetch(Long userId);
}
