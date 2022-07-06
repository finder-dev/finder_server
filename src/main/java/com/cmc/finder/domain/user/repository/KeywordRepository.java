package com.cmc.finder.domain.user.repository;

import com.cmc.finder.domain.user.entity.Keyword;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findByUser(User user);

}
