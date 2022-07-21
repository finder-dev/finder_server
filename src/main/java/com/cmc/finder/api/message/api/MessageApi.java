package com.cmc.finder.api.message.api;

import com.cmc.finder.api.message.application.ApiMessageService;
import com.cmc.finder.api.message.dto.*;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageApi {

    @Value("${page.count}")
    private Integer SET_PAGE_ITEM_MAX_COUNT;

    private final ApiMessageService apiMessageService;

    @PostMapping
    public ResponseEntity<ApiResult<CreateMessageDto.Response>> sendMessage(
            @RequestBody @Valid CreateMessageDto.Request request,
            @UserEmail String email
    ) {

        CreateMessageDto.Response response = apiMessageService.sendMessage(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping
    public ResponseEntity<ApiResult<Slice<GetConversationDto.Response>>> getMessagesByToUser(
            @Valid GetConversationDto.Request request,
            @UserEmail String email,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetConversationDto.Response> response = apiMessageService.getMessageByOther(email, request, pageable);

        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PostMapping("/report")
    public ResponseEntity<ApiResult<ReportUserDto.Response>> reportUser(
            @RequestBody @Valid ReportUserDto.Request request,
            @UserEmail String email
    ) {
        ReportUserDto.Response response = apiMessageService.reportUser(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @DeleteMapping
    public ResponseEntity<ApiResult<DeleteMessageDto.Response>> deleteMessage(
            @RequestBody @Valid DeleteMessageDto.Request request,
            @UserEmail String email
    ) {
        DeleteMessageDto.Response response = apiMessageService.deleteMessage(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/block")
    public ResponseEntity<ApiResult<BlockUserDto.Response>> blockUser(
            @RequestBody @Valid BlockUserDto.Request request,
            @UserEmail String email
    ) {
        BlockUserDto.Response response = apiMessageService.blockUser(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }


}
