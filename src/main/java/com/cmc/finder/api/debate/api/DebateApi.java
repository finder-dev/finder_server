package com.cmc.finder.api.debate.api;

import com.cmc.finder.api.debate.dto.DebateCreateDto;
import com.cmc.finder.api.debate.service.ApiDebateService;
import com.cmc.finder.global.resolver.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DebateApi {

    private final ApiDebateService apiDebateService;

    @PostMapping("/debates")
    public ResponseEntity<DebateCreateDto.Response> createDebate(
            @Valid DebateCreateDto.Request request,
            @UserEmail String email
    ) {

        DebateCreateDto.Response response = apiDebateService.createDebate(request, email);
        return ResponseEntity.ok(response);

    }

}
