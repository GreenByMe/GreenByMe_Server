package org.greenbyme.angelhack.domain.postlike;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.post.QPost;
import org.greenbyme.angelhack.domain.user.QUser;

import javax.persistence.EntityManager;
import java.util.List;

import static org.greenbyme.angelhack.domain.postlike.QPostLike.*;

public class PostLikeQueryDslImpl implements PostLikeQueryDsl {

    private final JPAQueryFactory queryFactory;

    public PostLikeQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostLike> findAllByPostId(Long postId) {
        return queryFactory
                .selectFrom(postLike)
                .leftJoin(postLike.post, QPost.post).fetchJoin()
                .leftJoin(postLike.user, QUser.user).fetchJoin()
                .where(postLike.post.id.eq(postId))
                .fetch();
    }


}
