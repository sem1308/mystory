package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Post;
import uos.mystory.domain.QPost;
import uos.mystory.dto.mapping.select.QSelectCategoryInfoDTO;
import uos.mystory.dto.mapping.select.QSelectPostInfoDTO;
import uos.mystory.dto.mapping.select.SelectPostInfoDTO;
import uos.mystory.repository.condition.PostSearchCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static uos.mystory.domain.QPost.post;

@Repository
public class PostQueryRepository extends Querydsl4RepositorySupport<Post, QPost>{

    public PostQueryRepository() {
        super(Post.class, post);
    }

    public Page<SelectPostInfoDTO> findAll(@NotNull PostSearchCondition condition, @NotNull Pageable pageable) {
        //== 자동 countQuery 생성 방법==//
        return applyPagination(pageable,
                select(new QSelectPostInfoDTO(
                        post.id,
                        post.postType,
                        post.writeType,
                        post.openState,
                        post.title,
                        post.content,
                        post.url,
                        post.titleImgPath,
                        post.hearts,
                        post.visits,
                        post.createdDateTime,
                        new QSelectCategoryInfoDTO(
                                post.category.id,
                                post.category.name
                        )
                ))
                        .from(post)
                        .where(
                                blogIdEq(condition.blogId())
                        ));
    }

    public List<SelectPostInfoDTO> findAll(@NotNull PostSearchCondition condition) {
        //== 자동 countQuery 생성 방법==//
        return select(new QSelectPostInfoDTO(
                    post.id,
                    post.postType,
                    post.writeType,
                    post.openState,
                    post.title,
                    post.content,
                    post.url,
                    post.titleImgPath,
                    post.hearts,
                    post.visits,
                    post.createdDateTime,
                    new QSelectCategoryInfoDTO(
                            post.category.id,
                            post.category.name
                    )
                ))
                .from(post)
                .where(
                        blogIdEq(condition.blogId())
                )
                .fetch();
    }

    public Optional<SelectPostInfoDTO> findById(Long postId) {
        //== 자동 countQuery 생성 방법==//
        return Optional.ofNullable(
                select(new QSelectPostInfoDTO(
                        post.id,
                        post.postType,
                        post.writeType,
                        post.openState,
                        post.title,
                        post.content,
                        post.url,
                        post.titleImgPath,
                        post.hearts,
                        post.visits,
                        post.createdDateTime,
                        new QSelectCategoryInfoDTO(
                                post.category.id,
                                post.category.name
                        )
                ))
                .from(post)
                .where(
                        post.id.eq(postId)
                )
                .fetchOne());
    }


    private BooleanExpression blogIdEq(Long blogId) {
        return blogId != null ?  post.blog.id.eq(blogId) :null;
    }
    private BooleanExpression postIdEq(Long postId) {
        return postId != null ?  post.id.eq(postId) :null;
    }
}
