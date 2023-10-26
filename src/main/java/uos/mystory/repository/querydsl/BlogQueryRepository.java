package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.QBlog;
import uos.mystory.domain.QUser;
import uos.mystory.dto.mapping.select.QSelectBlogInfoDTO;
import uos.mystory.dto.mapping.select.SelectBlogInfoDTO;
import uos.mystory.repository.condition.BlogSearchCondition;

import java.util.List;

import static uos.mystory.domain.QBlog.blog;

@Repository
public class BlogQueryRepository extends Querydsl4RepositorySupport<Blog, QBlog>{

    public BlogQueryRepository() {
        super(Blog.class, blog);
    }

    public Page<SelectBlogInfoDTO> findAll(@NotNull BlogSearchCondition condition, @NotNull Pageable pageable) {
        //== 자동 countQuery 생성 방법==//
        return applyPagination(pageable,
                select(new QSelectBlogInfoDTO(
                        blog.id,
                        blog.name,
                        blog.url,
                        blog.description,
                        blog.visits
                ))
                        .from(blog)
                        .where(
                                userIdEq(condition.userId())
                        ));

        //== Expression 방법==//
//        return applyPagination(pageable,
//                // content expression - select 변수
//                new QSelectBlogInfoDTO(
//                        blog.id,
//                        blog.name,
//                        blog.url,
//                        blog.description,
//                        blog.visits
//                ),
//                // count expression - select 변수
//                blog.count(),
//                // 공통 조건 query
//                from(blog)
//                        .where(
//                                userIdEq(condition.userId())
//                        ));

        //== 공통 JPAQuery 방법==//
//        return applyPagination(pageable,
//                select(new QSelectBlogInfoDTO(
//                        blog.id,
//                        blog.name,
//                        blog.url,
//                        blog.description,
//                        blog.visits
//                )),
//                select(blog.count()),
//                // 공통 조건 query
//                from(blog)
//                        .where(
//                                userIdEq(condition.userId())
//                        ));
    }

    public List<SelectBlogInfoDTO> findAll(@NotNull BlogSearchCondition condition) {
        //== 자동 countQuery 생성 방법==//
        return select(new QSelectBlogInfoDTO(
                        blog.id,
                        blog.name,
                        blog.url,
                        blog.description,
                        blog.visits
                ))
                .from(blog)
                .where(
                        userIdEq(condition.userId())
                )
                .fetch();
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ?  QUser.user.id.eq(userId) :null;
    }
}
