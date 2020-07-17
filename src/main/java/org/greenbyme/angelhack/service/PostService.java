package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoRepository;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.post.PostRepository;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.*;
import org.greenbyme.angelhack.service.dto.post.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MissionInfoRepository missionInfoRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public PostSaveResponseDto savePosts(final PostSaveRequestDto requestDto, String fileUrl) {
        MissionInfo missionInfo = missionInfoRepository.findById(requestDto.getMissionInfoId())
                .orElseThrow(() -> new MissionException(ErrorCode.INVALID_MISSIONINFO));
        if (postRepository.findByMissionInfo(missionInfo) != null) {
            throw new AlreadyExistsPostException();
        }
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        Post savePost = new Post(user, missionInfo, requestDto.getText(), requestDto.getTitle(), fileUrl, requestDto.getOpen());
        savePost = postRepository.save(savePost);
        return new PostSaveResponseDto(savePost.getId());
    }

    public List<PostResponseDto> getPostsByMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(ErrorCode.INVALID_MISSION));
        return missionInfoRepository.findAllByMission(mission)
                .stream()
                .map(postRepository::findByMissionInfo)
                .map(p -> new PostResponseDto(p.getId(), p.getUser().getNickname(), p.getPicture(), p.getThumbsUp()))
                .collect(Collectors.toList());
    }

    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.INVALID_POST));
        return new PostDetailResponseDto(post);
    }

    public List<PostResponseDto> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        return postRepository.findAllByUser(user)
                .stream()
                .map(p -> new PostResponseDto(p.getId(), user.getNickname(), p.getPicture(), p.getThumbsUp()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostSaveResponseDto updatePost(Long postId, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.INVALID_POST));
        post.update(requestDto);
        return new PostSaveResponseDto(postId);
    }

    @Transactional
    public void thumbsUp(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.INVALID_POST));
        post.thumbsUp();
    }
}
