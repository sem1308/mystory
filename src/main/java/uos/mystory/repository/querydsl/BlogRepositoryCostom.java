package uos.mystory.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uos.mystory.dto.response.BlogInfoDTO;
import uos.mystory.repository.condition.BlogSearchCondition;

public interface BlogRepositoryCostom {
    Page<BlogInfoDTO> findAll(BlogSearchCondition condition, Pageable pageable);
}
