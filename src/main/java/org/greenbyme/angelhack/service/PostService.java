package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.post.PostRepository;
import org.greenbyme.angelhack.exception.AlreadyExistsPostException;
import org.greenbyme.angelhack.service.dto.PostResponseDto;
import org.greenbyme.angelhack.service.dto.post.PostSaveRequestDto;
import org.greenbyme.angelhack.service.dto.post.PostSaveResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostSaveResponseDto savePosts(final PostSaveRequestDto requestDto) {
        if (postRepository.findByMissionInfo(requestDto.getMissionInfo()) != null) {
            throw new AlreadyExistsPostException();
        }
        Post savePost = postRepository.save(requestDto.toEntity());
        PostSaveResponseDto responseDto = new PostSaveResponseDto(savePost.getId());
        return responseDto;
    }

    public List<PostResponseDto> getPostsByMission(Long missionId) {
        List<Post> posts = postRepository.findAll()
                .stream()
                .filter(p -> p.getMissionInfo().getMission().getId().equals(missionId))
                .collect(Collectors.toList());

    }
}
