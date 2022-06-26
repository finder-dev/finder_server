package com.cmc.finder.api.health;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HealthCheckApi {

    @GetMapping
    public String healthCheck() {
        return "I'm OK...";
    }

}
