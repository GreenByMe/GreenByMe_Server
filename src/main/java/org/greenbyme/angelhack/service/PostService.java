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
import org.greenbyme.angelhack.domain.posttag.PostTag;
import org.greenbyme.angelhack.domain.posttag.PostTagRepository;
import org.greenbyme.angelhack.domain.tag.Tag;
import org.greenbyme.angelhack.domain.tag.TagRepository;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.*;
import org.greenbyme.angelhack.service.dto.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;

    @Autowired
    private FileUploadDownloadService service;

    @Transactional
    public PostSaveResponseDto savePosts(Long userId, PostSaveRequestDto requestDto, MultipartFile file) {
        PersonalMission personalMission = personalMissionRepository.findDetailsById(requestDto.getPersonalMission_id())
                .orElseThrow(() -> new MissionException(ErrorCode.INVALID_PERSONAL_MISSION));
        if (!personalMission.getUser().getId().equals(userId)) {
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
                .user(personalMission.getUser())
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
            personalMission.getMission().addCumulativeCo2Tree();
        }
        if (requestDto.getTags() != null) {
            List<Tag> tags = requestDto.getTags().stream()
                    .map(this::saveTag)
                    .collect(Collectors.toList());
            List<PostTag> postTags = tags.stream()
                    .map(t -> savePostTag(savedPost, t))
                    .collect(Collectors.toList());
            savedPost.addTags(postTags);
        }
        double expectTree = personalMission.getMission().getExpectTree();
        personalMission.getUser().addExpectCo2(personalMission.getMission().getExpectTree());
        int finishCount = personalMission.getFinishCount();
        return new PostSaveResponseDto(savedPost.getId(), personalMission.getUser().getNickname(), expectTree, finishCount);
    }

    public Page<PostResponseDto> getPostsByMission(Long missionId, Pageable pageable) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(ErrorCode.INVALID_MISSION));
        List<PostResponseDto> res = personalMissionRepository.findAllByMissionId(mission.getId(), pageable)
                .map(p -> postRepository.findByPersonalMissionId(p.getId()))
                .stream()
                .filter(Objects::nonNull)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(res, pageable, res.size());
    }

    public PostDetailResponseDto getPostDetail(Long postId, Long userId) {
        Post post = getPost(postId);

        boolean mine = false;
        if (post.getUser().getId() == userId) {
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
        if (post.getUser().getId()!= userId) {
            throw new PostException(ErrorCode.INVALID_POST_ACCESS);
        }
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long userId, Long postId, PostUpdateRequestDto requestDto) {
        Post post = getPost(postId);
        if (post.getUser().getId()!= userId) {
            throw new PostException(ErrorCode.INVALID_POST_ACCESS);
        }
        post.update(requestDto);
        return new PostUpdateResponseDto(post);
    }

    @Transactional
    public boolean thumbsUp(Long postId, Long userId) {
        Post post = getPost(postId);
        User user = getUser(userId);
        if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
            PostLike postLike = postLikeRepository.findByUserAndPost(user, post).get();
            postLike.remove();
            postLikeRepository.delete(postLike);
            return false;
        } else {
            postLikeRepository.save(new PostLike(post, user));
            return true;
        }
    }

    private Post getPost(Long postId) {
        return postRepository.findByIdWithFetch(postId)
                .orElseThrow(() -> new PostException(ErrorCode.INVALID_POST));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
    }

    @Transactional
    public Tag saveTag(String tagName) {
        if (tagRepository.findByTagName(tagName).isPresent()) {
            return tagRepository.findByTagName(tagName).get();
        }
        return tagRepository.save(new Tag(tagName));
    }

    @Transactional
    public PostTag savePostTag(Post post, Tag tag) {
        if (postTagRepository.findByPostAndTag(post, tag).isPresent()) {
            return postTagRepository.findByPostAndTag(post, tag).get();
        }
        return postTagRepository.save(new PostTag(post, tag));
    }

    public PostByTagResponseDto getPostsByTag(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName)
                .orElseThrow(() -> new TagException(ErrorCode.INVALID_TAG));
        List<PostTag> postTags = postTagRepository.findAllByTag(tag);
        List<PostResponseDto> res = postTags.stream()
                .map(PostTag::getPost)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        return new PostByTagResponseDto(tagName, res);
    }
}
