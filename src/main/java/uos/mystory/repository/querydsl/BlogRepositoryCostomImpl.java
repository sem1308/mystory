package uos.mystory.repository.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.QBlog;
import uos.mystory.domain.QUser;
import uos.mystory.dto.response.BlogInfoDTO;
import uos.mystory.dto.response.QBlogInfoDTO;
import uos.mystory.repository.condition.BlogSearchCondition;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlogRepositoryCostomImpl implements BlogRepositoryCostom {
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
                .limit(pageable.getPageSize())
                .orderBy(getAllOrderSpecifiers(pageable.getSort()));

        List<BlogInfoDTO> content = query.fetch();
        long total = content.size();

        return new PageImpl<>(content, pageable, total);
    }

    private OrderSpecifier<?>[] getAllOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : sort) {
            String property = order.getProperty(); // 정렬 대상 필드 이름
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC; // 정렬 방향 (ASC 또는 DESC)

            orderSpecifiers.add(new OrderSpecifier(direction, new PathBuilder<>(Blog.class,"blog").get(property)));
        }

        // 정렬 조건을 결합
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }


    private BooleanExpression userIdEq(Long userId) {
        return userId != null ?  QUser.user.id.eq(userId) :null;
    }
}
