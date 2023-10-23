package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.QUser;
import uos.mystory.dto.response.BlogInfoDTO;
import uos.mystory.dto.response.QBlogInfoDTO;
import uos.mystory.repository.condition.BlogSearchCondition;

import static uos.mystory.domain.QBlog.blog;

@Repository
public class BlogQueryRepository extends Querydsl4RepositorySupport{

    public BlogQueryRepository() {
        super(Blog.class);
    }

    public Page<BlogInfoDTO> findAll(@NotNull BlogSearchCondition condition, @NotNull Pageable pageable) {
        return applyPagination(pageable,
                contentQuery->contentQuery
                        .select(new QBlogInfoDTO(
                                blog.id,
                                blog.name,
                                blog.url,
                                blog.description,
                                blog.visits
                        ))
                , countQuery->countQuery
                        .select(blog.count())
                , commonQuery->
                        (JPAQuery) commonQuery
                        .from(blog)
                        .where(
                                userIdEq(condition.userId())
                        )
        );
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ?  QUser.user.id.eq(userId) :null;
    }
}
