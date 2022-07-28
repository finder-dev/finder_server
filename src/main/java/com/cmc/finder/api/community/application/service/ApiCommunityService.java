package com.cmc.finder.api.community.application.service;

import com.cmc.finder.api.community.dto.*;
import com.cmc.finder.domain.block.application.BlockService;
import com.cmc.finder.domain.community.application.*;
import com.cmc.finder.domain.community.entity.*;
import com.cmc.finder.domain.community.exception.CommunityImageExceedNumberException;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.report.application.ReportService;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.report.exception.AlreadyReceivedReportException;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
import com.cmc.finder.global.advice.CheckCommunityAdmin;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final ReportService reportService;
    private final BlockService blockService;

    private final S3Uploader s3Uploader;

    @Transactional
    public CreateCommunityDto.Response createCommunity(CreateCommunityDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 커뮤니티 생성
        Community community = request.toEntity();
        Community saveCommunity = Community.createCommunity(community, user);

        // 이미지 생성 및 저장
        request.getCommunityImages().forEach(multipartFile -> {

            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            CommunityImage communityImage = CommunityImage.createCommunityImage(community, imageName, url);
            saveCommunity.addCommunityImage(communityImage);

        });

        // 커뮤니티 저장
        Community savedCommunity = communityService.createCommunity(saveCommunity);

        return CreateCommunityDto.Response.from(savedCommunity);

    }

    public Slice<CommunitySimpleDto.Response> getCommunityList(Pageable pageable, String mbti, String email) {
        User user = userService.getUserByEmail(Email.of(email));
        return communityService.getCommunityList(pageable, mbti, user);

    }

    public List<GetHotCommunityRes> getHotCommunityList(String email) {
        User user = userService.getUserByEmail(Email.of(email));

        List<Community> hotCommunity = communityService.getHotCommunity(user);
        return hotCommunity.stream().map(GetHotCommunityRes::from)
                .collect(Collectors.toList());

    }


    public CommunityDetailDto getCommunityDetail(Long communityId, String email) {

        // 유저 조회 -> 조회수 증가
        User user = userService.getUserByEmail(Email.of(email));

        // 커뮤니티 조회
        Community community = communityService.getCommunityFetchUser(communityId);

        // 신고 조회
        List<Long> reportedServiceId = reportService.getReportsByUser(user, ServiceType.COMMUNITY_ANSWER);

        // 차단 조회
        List<User> blockedUser = blockService.getBlockUser(user);

        // 답변 조회 -> id 역순
        List<CommunityAnswer> answers = communityAnswerService.getAnswersByCommunity(community, user);

        // 이미 저장?
        Boolean saveUser = saveCommunityService.existsUser(community, user);

        // 이미 좋아요?
        Boolean likeUser = likeService.existsUser(community, user);

        return CommunityDetailDto.of(community, answers, likeUser, saveUser, reportedServiceId, blockedUser);
    }

    @CheckCommunityAdmin
    @Transactional
    public UpdateCommunityDto.Response updateCommunity(Long communityId, String email, UpdateCommunityDto.Request request) {

        // 질문 정보 변경
        Community updatedCommunity = updateCommunityInfo(communityId, request);
        updateCommunityImages(updatedCommunity, request);

        // 이미지 10개 초과 검증
        if (updatedCommunity.getCommunityImages().size() > 10) {
            throw new CommunityImageExceedNumberException(ErrorCode.COMMUNITY_IMAGE_EXCEED_NUMBER);
        }

        return UpdateCommunityDto.Response.from(updatedCommunity);
    }

    private Community updateCommunityInfo(Long communityId, UpdateCommunityDto.Request request) {

        Community updateCommunity = request.toEntity();
        Community updatedCommunity = communityService.updateCommunity(communityId, updateCommunity);

        return updatedCommunity;

    }

    private void updateCommunityImages(Community community, UpdateCommunityDto.Request request) {

        // 질문 이미지 삭제
        request.getDeleteImageIds().forEach(deleteImgId -> {

            CommunityImage communityImage = communityImageService.getCommunityImage(deleteImgId);
            s3Uploader.deleteFile(communityImage.getImageName(), PATH);
            community.deleteCommunityImage(communityImage);

        });


        // 질문 이미지 추가
        request.getAddImages().forEach(multipartFile -> {

            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            CommunityImage communityImage = CommunityImage.createCommunityImage(community, imageName, url);
            community.addCommunityImage(communityImage);

        });

    }

    @CheckCommunityAdmin
    @Transactional
    public DeleteCommunityRes deleteCommunity(Long communityId, String email) {

        Community community = communityService.getCommunity(communityId);
        communityService.deleteCommunity(community);

        // 질문 이미지 S3 삭제
        community.getCommunityImages().forEach(communityImage -> {
            s3Uploader.deleteFile(communityImage.getImageName(), PATH);
        });

        return DeleteCommunityRes.of();
    }

    @Transactional
    public AddOrDeleteLikeRes addOrDeleteLike(Long communityId, String email) {

        Community community = communityService.getCommunity(communityId);
        User user = userService.getUserByEmail(Email.of(email));

        if (likeService.existsUser(community, user)) {
            likeService.deleteLike(community, user);
            return AddOrDeleteLikeRes.from(false);
        }

        Like like = Like.createLike(community, user);
        community.addLike(like);

        likeService.addLike(like);

        return AddOrDeleteLikeRes.from(true);

    }

    public Slice<CommunitySearchDto.Response> searchCommunity(String search, Pageable pageable, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        return communityService.getSearchCommunityList(pageable, search, user);

    }

    @Transactional
    public SaveOrRemoveCommunityRes saveOrRemoveCommunity(Long communityId, String email) {

        Community community = communityService.getCommunity(communityId);
        User user = userService.getUserByEmail(Email.of(email));

        if (saveCommunityService.existsUser(community, user)) {
            saveCommunityService.removeCommunity(community, user);
            return SaveOrRemoveCommunityRes.from(false);
        }

        SaveCommunity saveCommunity = SaveCommunity.createSaveCommunity(community, user);
        community.addSaveCommunity(saveCommunity);

        return SaveOrRemoveCommunityRes.from(true);

    }


    @Transactional
    public ReportCommunityRes reportCommunity(Long communityId, String email) {

        Community community = communityService.getCommunityFetchUser(communityId);
        User from = userService.getUserByEmail(Email.of(email));

        Report report = Report.createReport(ServiceType.COMMUNITY, from, community.getUser(), communityId);

        if (reportService.alreadyReceivedReport(report)) {
            throw new AlreadyReceivedReportException(ErrorCode.ALREADY_RECEIVED_REPORT);
        }

        reportService.create(report);

        return ReportCommunityRes.of();

    }
}
