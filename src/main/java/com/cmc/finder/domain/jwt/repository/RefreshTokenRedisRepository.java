package com.cmc.finder.domain.jwt.repository;

import com.cmc.finder.domain.jwt.domain.RefreshToken;
import com.cmc.finder.domain.model.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

}
