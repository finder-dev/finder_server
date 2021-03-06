package com.cmc.finder.domain.user.repository;

import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(Email email);

    boolean existsByEmail(Email email);

    boolean existsByNickname(String nickname);

}
