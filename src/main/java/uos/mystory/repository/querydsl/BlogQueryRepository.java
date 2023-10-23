package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
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
                // content expression - select 변수
                new QBlogInfoDTO(
                        blog.id,
                        blog.name,
                        blog.url,
                        blog.description,
                        blog.visits
                ),
                // count expression - select 변수
                blog.count(),
                // 공통 조건 query
                from(blog)
                        .where(
                                userIdEq(condition.userId())
                        ));
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ?  QUser.user.id.eq(userId) :null;
    }
}
