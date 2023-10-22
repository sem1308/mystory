package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.QBlog;
import uos.mystory.domain.QUser;
import uos.mystory.dto.response.BlogInfoDTO;
import uos.mystory.dto.response.QBlogInfoDTO;
import uos.mystory.repository.condition.BlogSearchCondition;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlogRepositoryCostomImpl {
    private final JPAQueryFactory queryFactory; // 물론 이를 위해서는 빈으로 등록을 해줘야 한다.
    private final QBlog blog = QBlog.blog;

    public Page<BlogInfoDTO> findAll(@NotNull BlogSearchCondition condition, @NotNull Pageable pageable) {
        JPAQuery<BlogInfoDTO> query = queryFactory
                .select(new QBlogInfoDTO(
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<BlogInfoDTO> content = query.fetch();
        long total = content.size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ?  QUser.user.id.eq(userId) :null;
    }
}
