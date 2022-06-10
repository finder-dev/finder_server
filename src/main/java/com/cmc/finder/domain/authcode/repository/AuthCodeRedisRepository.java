package com.cmc.finder.domain.authcode.repository;

import com.cmc.finder.domain.authcode.entity.AuthCode;
import org.springframework.data.repository.CrudRepository;

public interface AuthCodeRedisRepository extends CrudRepository<AuthCode, String> {

}
