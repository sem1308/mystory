package uos.mystory.repository.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

@Repository
public abstract class Querydsl4RepositorySupport {
    private final Class domainClass;
    private Querydsl querydsl;
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;
    private boolean hasNext;

    public Querydsl4RepositorySupport(Class<?> domainClass) {
        Assert.notNull(domainClass,"Domain class must not be null.");
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        JpaEntityInformation entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath path = resolver.createPath(entityInformation.getJavaType());
        this.entityManager = entityManager;
        this.querydsl = new Querydsl(entityManager, new PathBuilder<>(path.getType(), path.getMetadata()));
    }

    @Autowired
    public void setQueryFactory(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }
    protected JPAQueryFactory getQueryFactory() {
        return queryFactory;
    }
    protected Querydsl getQuerydsl() {
        return querydsl;
    }
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected <T> JPAQuery<T> select(Expression<T> expr) {
        return getQueryFactory().select(expr);
    }

    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return getQueryFactory().selectFrom(from);
    }

    protected <T> Page<T> applyPagination(Pageable pageable, Function<JPAQueryFactory, JPAQuery> contentQuery, Function<JPAQueryFactory,JPAQuery> countQuery) {
        JPAQuery jpaContentQuery = contentQuery.apply(getQueryFactory());
        List<T> content = getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();
        JPAQuery countResultQuery = countQuery.apply(getQueryFactory());
        List countResult = countResultQuery.fetch();

        return PageableExecutionUtils.getPage(content, pageable, countResult::size);
    }

    //== 보통 paging시 contentQuery와 countQuery의 기본 조건이 같기에 이러한 조건을 중복해서 쓰는 것을 피하기 위한 함수 ==//
    protected <T> Page<T> applyPaginationByCommonQuery(Pageable pageable, Function<JPAQueryFactory, JPAQuery> contentQuery,
                                                           Function<JPAQueryFactory,JPAQuery> countQuery, Function<JPAQuery, JPAQuery> commonQuery) {
        JPAQuery jpaContentQuery = contentQuery.apply(getQueryFactory());
        jpaContentQuery = commonQuery.apply(jpaContentQuery); // 공통 조건 쿼리 추가
        List<T> content = getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();
        JPAQuery<Long> countResultQuery = countQuery.apply(getQueryFactory());
        countResultQuery = commonQuery.apply(countResultQuery); // 공통 조건 쿼리 추가

        return PageableExecutionUtils.getPage(content, pageable, countResultQuery::fetchOne);
    }
}
