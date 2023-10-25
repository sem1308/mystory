package uos.mystory.repository.querydsl.regacy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uos.mystory.dto.mapping.select.SelectBlogInfoDTO;
import uos.mystory.repository.condition.BlogSearchCondition;

public interface BlogRepositoryCostom {
    Page<SelectBlogInfoDTO> findAll(BlogSearchCondition condition, Pageable pageable);
}
