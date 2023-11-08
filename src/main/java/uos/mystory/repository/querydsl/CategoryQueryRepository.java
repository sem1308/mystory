package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Category;
import uos.mystory.domain.QCategory;
import uos.mystory.dto.mapping.select.QSelectCategoryInfoDTO;
import uos.mystory.dto.mapping.select.SelectCategoryInfoDTO;

import java.util.List;

import static uos.mystory.domain.QCategory.category;

@Repository
public class CategoryQueryRepository extends Querydsl4RepositorySupport<Category, QCategory>{

    public CategoryQueryRepository() {
        super(Category.class, category);
    }

    public List<SelectCategoryInfoDTO> findAll(Long blogId) {
        //== 자동 countQuery 생성 방법==//
        return select(new QSelectCategoryInfoDTO(
                        category.id,
                        category.name
                ))
                .from(category)
                .where(
                        blogIdEq(blogId)
                )
                .fetch();
    }

    private BooleanExpression blogIdEq(Long blogId) {
        return blogId != null ?  category.blog.id.eq(blogId) :null;
    }
}
