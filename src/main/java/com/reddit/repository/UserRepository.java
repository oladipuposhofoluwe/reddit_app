package com.reddit.repository;//package com.reddit.app.repository;

import com.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findAuthUserByEmail(String username);

    Optional<User> findByUserName(String username);

    Optional<User> findByUsername(String email);
}
