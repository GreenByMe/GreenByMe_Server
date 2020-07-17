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
import org.greenbyme.angelhack.exception.AlreadyExistsPostException;
import org.greenbyme.angelhack.service.dto.post.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public PostSaveResponseDto savePosts(final PostSaveRequestDto requestDto) {
        MissionInfo missionInfo = missionInfoRepository.findById(requestDto.getMissionInfoId()).get();
        if (postRepository.findByMissionInfo(missionInfo) != null) {
            throw new AlreadyExistsPostException();
        }
        User user = userRepository.findById(requestDto.getUserId()).get();
        Post savePost = new Post(user, missionInfo, requestDto.getText(), requestDto.getTitle(), requestDto.getPicture(), requestDto.getOpen());
        savePost = postRepository.save(savePost);
        PostSaveResponseDto responseDto = new PostSaveResponseDto(savePost.getId());
        return responseDto;
    }

    public List<PostResponseDto> getPostsByMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId).get();
        List<PostResponseDto> dto = missionInfoRepository.findAllByMission(mission)
                .stream()
                .map(m -> postRepository.findByMissionInfo(m))
                .map(p -> new PostResponseDto(p.getId(), p.getUser().getNickname(), p.getPicture(), p.getThumbsUp()))
                .collect(Collectors.toList());
        return dto;
    }

    public PostDetailResponseDto getPostDetail(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        PostDetailResponseDto responseDto = new PostDetailResponseDto(post.get());
        return responseDto;
    }

    public List<PostResponseDto> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId).get();
        List<PostResponseDto> dto = postRepository.findAllByUser(user)
                .stream()
                .map(p -> new PostResponseDto(p.getId(), user.getNickname(), p.getPicture(), p.getThumbsUp()))
                .collect(Collectors.toList());
        return dto;
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostSaveResponseDto updatePost(Long postId, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(postId).get();
        post.update(requestDto);
        return new PostSaveResponseDto(postId);
    }
}
