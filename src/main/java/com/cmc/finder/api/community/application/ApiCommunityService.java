package com.cmc.finder.api.community.application;

import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.api.community.dto.CreateCommunityDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionCreateDto;
import com.cmc.finder.domain.community.application.CommunityService;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityImage;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.entity.QuestionImage;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiCommunityService {

    @Value("${s3.community.path}")
    private String PATH;

    private final UserService userService;
    private final CommunityService communityService;
    private final S3Uploader s3Uploader;

    @Transactional
    public CreateCommunityDto.Response createCommunity(CreateCommunityDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 커뮤니티 생성
        Community community = request.toEntity();
        Community saveCommunity = Community.createCommunity(community, user);

        // 질문 이미지 생성 및 저장
        request.getCommunityImgs().stream().forEach(multipartFile -> {
            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            CommunityImage communityImage = CommunityImage.createCommunityImage(community, imageName, url);
            saveCommunity.addCommunityImage(communityImage);

        });

        // 질문 저장
        Community savedCommunity = communityService.createCommunity(saveCommunity);

        return CreateCommunityDto.Response.of(savedCommunity);

    }

    public Page<CommunitySimpleDto.Response> getCommunityList(Pageable pageable, MBTI mbti) {
        return communityService.getCommunityList(pageable, mbti);
    }
}
