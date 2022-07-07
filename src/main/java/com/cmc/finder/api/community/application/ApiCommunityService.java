package com.cmc.finder.api.community.application;

import com.cmc.finder.api.community.dto.*;
import com.cmc.finder.domain.community.application.*;
import com.cmc.finder.domain.community.entity.*;
import com.cmc.finder.domain.community.exception.CommunityImageExceedNumberException;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiCommunityService {

    @Value("${s3.community.path}")
    private String PATH;

    private final UserService userService;
    private final CommunityService communityService;
    private final CommunityAnswerService communityAnswerService;
    private final CommunityImageService communityImageService;
    private final SaveCommunityService saveCommunityService;
    private final LikeService likeService;

    private final S3Uploader s3Uploader;

    @Transactional
    public CreateCommunityDto.Response createCommunity(CreateCommunityDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 커뮤니티 생성
        Community community = request.toEntity();
        Community saveCommunity = Community.createCommunity(community, user);

        // 이미지 생성 및 저장
        request.getCommunityImages().stream().forEach(multipartFile -> {
            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            CommunityImage communityImage = CommunityImage.createCommunityImage(community, imageName, url);
            saveCommunity.addCommunityImage(communityImage);

        });

        // 커뮤니티 저장
        Community savedCommunity = communityService.createCommunity(saveCommunity);

        return CreateCommunityDto.Response.of(savedCommunity);

    }

    public Page<CommunitySimpleDto.Response> getCommunityList(Pageable pageable, String mbti, String email) {
        User user = userService.getUserByEmail(Email.of(email));

        return communityService.getCommunityList(pageable, mbti, user);

    }

    public List<GetHotCommunityRes> getHotCommunity() {
        List<Community> hotCommunity = communityService.getHotCommunity();
        return hotCommunity.stream().map(community ->
                        GetHotCommunityRes.of(community)).
                collect(Collectors.toList());

    }


    public CommunityDetailDto getCommunityDetail(Long communityId, String email) {

        // 유저 조회 -> 조회수 증가
        User user = userService.getUserByEmail(Email.of(email));

        // 커뮤니티 조회
        Community community = communityService.getCommunityFetchUser(communityId);

        // 답변 조회 -> id 역순
        List<CommunityAnswer> answers = communityAnswerService.getAnswersByCommunityId(community.getCommunityId());

        // 이미 좋아요?
        Boolean likeUser = likeService.existsUser(community, user);

        return CommunityDetailDto.of(community, answers, likeUser);
    }


    @Transactional
    public UpdateCommunityDto.Response updateCommunity(Long communityId, UpdateCommunityDto.Request request, String email) {


        User user = userService.getUserByEmail(Email.of(email));
        Community community = communityService.getCommunityFetchUser(communityId);

        // 유저 검증
        if (community.getUser() != user) {
            throw new AuthenticationException(ErrorCode.COMMUNITY_USER_NOT_WRITER);
        }

        // 질문 정보 변경
        Community updatedCommunity = updateCommunityInfo(communityId, request);
        updateCommunityImages(updatedCommunity, request);

        // 이미지 10개 초과 검증
        if (updatedCommunity.getCommunityImages().size() > 10) {
            throw new CommunityImageExceedNumberException(ErrorCode.COMMUNITY_IMAGE_EXCEED_NUMBER);
        }

        return UpdateCommunityDto.Response.of(updatedCommunity);
    }

    private Community updateCommunityInfo(Long communityId, UpdateCommunityDto.Request request) {

        Community updateCommunity = request.toEntity();
        Community updatedCommunity = communityService.updateCommunity(communityId, updateCommunity);

        return updatedCommunity;

    }

    private void updateCommunityImages(Community community, UpdateCommunityDto.Request request) {

        // 질문 이미지 삭제
        request.getDeleteImageIds().stream().forEach(deleteImgId -> {

            CommunityImage communityImage = communityImageService.getCommunityImage(deleteImgId);
            s3Uploader.deleteFile(communityImage.getImageName(), PATH);
            community.deleteCommunityImage(communityImage);

        });

        // 질문 이미지 추가
        request.getAddImages().stream().forEach(multipartFile -> {

            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            CommunityImage communityImage = CommunityImage.createCommunityImage(community, imageName, url);
            CommunityImage savedCommunityImage = communityImageService.save(communityImage);
            community.addCommunityImage(savedCommunityImage);

        });

    }


    @Transactional
    public DeleteCommunityRes deleteCommunity(Long communityId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Community community = communityService.getCommunityFetchUser(communityId);

        // 유저 검증
        if (community.getUser() != user) {
            throw new AuthenticationException(ErrorCode.COMMUNITY_USER_NOT_WRITER);
        }

        communityService.deleteCommunity(community);

        return DeleteCommunityRes.of();
    }

    @Transactional
    public AddOrDeleteLikeRes addOrDeleteLike(Long communityId, String email) {

        Community community = communityService.getCommunity(communityId);
        User user = userService.getUserByEmail(Email.of(email));

        if (likeService.existsUser(community, user)) {
            likeService.deleteLike(community, user);
            return AddOrDeleteLikeRes.of(false);
        }


        Like like = Like.createLike(community, user);
        community.addLike(like);

        likeService.addLike(like);

        return AddOrDeleteLikeRes.of(true);

    }

    public Page<CommunitySearchDto.Response> searchCommunity(String search, Pageable pageable) {

        return communityService.getSearchCommunityList(pageable, search);

    }

    @Transactional
    public SaveOrRemoveCommunityRes saveOrRemoveCommunity(Long communityId, String email) {

        Community community = communityService.getCommunity(communityId);
        User user = userService.getUserByEmail(Email.of(email));

        if (saveCommunityService.existsUser(community, user)) {
            saveCommunityService.removeCommunity(community, user);
            return SaveOrRemoveCommunityRes.of(false);
        }

        SaveCommunity saveCommunity = SaveCommunity.createSaveCommunity(community, user);
        community.addSaveCommunity(saveCommunity);

        return SaveOrRemoveCommunityRes.of(true);

    }

    public GetCheckWriterRes checkWriter(Long communityId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Boolean check = communityService.isCommunityWriter(communityId, user);

        return GetCheckWriterRes.of(check);



    }
}
