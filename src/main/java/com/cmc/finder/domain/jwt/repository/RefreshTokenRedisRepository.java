package com.cmc.finder.domain.jwt.repository;

import com.cmc.finder.domain.jwt.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

}
