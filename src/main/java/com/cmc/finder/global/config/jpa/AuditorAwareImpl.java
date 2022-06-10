package com.cmc.finder.global.config.jpa;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {

        String modifiedBy = httpServletRequest.getRequestURI();
        if(!StringUtils.hasText(modifiedBy)){
            modifiedBy = "unknown";
        }

        return Optional.of(modifiedBy);
    }

}
