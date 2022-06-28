package com.cmc.finder.api.community.service;

import com.cmc.finder.api.community.dto.CommunityHotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiCommunityService {


    public List<CommunityHotDto> getHotCommunity() {

        List<CommunityHotDto> communityHotDtos = new ArrayList<>();

        communityHotDtos.add(CommunityHotDto.of(1L, "엔팁한테 사과받았다 대박!!!!!!!"));
        communityHotDtos.add(CommunityHotDto.of(2L, "INTJ 27년차 꿀팁 알려준다"));
        communityHotDtos.add(CommunityHotDto.of(3L, "깻잎논쟁 이해 안 되는 ㅇㅇ들 모여봐"));
        communityHotDtos.add(CommunityHotDto.of(4L, "잇프제랑 손절함"));
        communityHotDtos.add(CommunityHotDto.of(5L, "쩝쩝박사님들 점메뉴 부탁드립니다"));

        return communityHotDtos;
    }
}
