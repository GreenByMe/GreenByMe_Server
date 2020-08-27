package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionRepository;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionRepository;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.post.PostRepository;
import org.greenbyme.angelhack.domain.postlike.PostLike;
import org.greenbyme.angelhack.domain.postlike.PostLikeRepository;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.MissionException;
import org.greenbyme.angelhack.exception.PostException;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final PersonalMissionRepository personalMissionRepository;
    private final MissionRepository missionRepository;

    @Autowired
    private FileUploadDownloadService service;

    @Transactional
    public PostSaveResponseDto savePosts(Long userId, PostSaveRequestDto requestDto, MultipartFile file) {
        PersonalMission personalMission = personalMissionRepository.findDetailsById(requestDto.getPersonalMission_id())
                .orElseThrow(() -> new MissionException(ErrorCode.INVALID_PERSONAL_MISSION));
        User user = getUser(userId);
        if (!personalMission.getUser().getId().equals(user.getId())) {
            throw new PostException(ErrorCode.WRONG_ACCESS);
        }
        List<Post> posts = postRepository.findAllByPersonalMission(personalMission);
        long postCount = posts.stream()
                .map(p -> p.getLastModifiedDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
                .count();
        if (postCount > 0) {
            throw new PostException(ErrorCode.OVER_CERIFICATION);
        }

        String fileName = service.storeFile(file);
        String filedUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/posts/images/")
                .path(fileName)
                .toUriString();

        Post savePost = Post.builder()
                .user(user)
                .personalMission(personalMission)
                .text(requestDto.getText())
                .title(requestDto.getTitle())
                .picture(filedUrl)
                .open(requestDto.getOpen())
                .build();
        Post savedPost = postRepository.save(savePost);
        personalMission.addProgress();
        if (personalMission.isEnd()) {
            personalMission.getMission().addPassCandidates();
        }
        double expectTree = personalMission.getMission().getExpectTree();
        int finishCount = personalMission.getFinishCount();
        return new PostSaveResponseDto(savedPost.getId(), user.getNickname(), expectTree, finishCount);
    }

    public Page<PostResponseDto> getPostsByMission(Long missionId, Pageable pageable) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(ErrorCode.INVALID_MISSION));
        return personalMissionRepository.findAllByMission(mission, pageable)
                .map(postRepository::findByPersonalMission)
                .map(p -> new PostResponseDto(p.getId(), p.getUser().getNickname(), p.getPicture(), p.getPostLikes().size()));
    }

    public PostDetailResponseDto getPostDetail(Long postId, Long userId) {
        Post post = getPost(postId);
        User user = getUser(userId);
        boolean mine = false;
        if (post.getUser().equals(user)) {
            mine = true;
        }
        return new PostDetailResponseDto(post, mine);
    }

    public Page<PostResponseDto> getPostsByUser(Long userId, Pageable pageable) {
        User user = getUser(userId);
        return postRepository.findAllByUser(user, pageable)
                .map(p -> new PostResponseDto(p.getId(), user.getNickname(), p.getPicture(), p.getPostLikes().size()));
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = getPost(postId);
        User user = getUser(userId);
        if (!post.getUser().equals(user)) {
            throw new PostException(ErrorCode.INVALID_POST_ACCESS);
        }
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long userId, Long postId, PostUpdateRequestDto requestDto) {
        Post post = getPost(postId);
        User user = getUser(userId);
        if (!post.getUser().equals(user)) {
            throw new PostException(ErrorCode.INVALID_POST_ACCESS);
        }
        post.update(requestDto);
        return new PostUpdateResponseDto(post);
    }

    @Transactional
    public void thumbsUp(Long postId, Long userId) {
        Post post = getPost(postId);
        User user = getUser(userId);
        if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
            PostLike postLike = postLikeRepository.findByUserAndPost(user, post).get();
            postLike.remove();
            postLikeRepository.delete(postLike);
        } else {
            postLikeRepository.save(new PostLike(post, user));
        }
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.INVALID_POST));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
    }
}
