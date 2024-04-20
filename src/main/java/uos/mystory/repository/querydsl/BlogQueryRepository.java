package uos.mystory.repository.querydsl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uos.mystory.dto.mapping.select.SelectBlogInfoDTO;
import uos.mystory.repository.condition.BlogSearchCondition;

import java.util.List;

public interface BlogQueryRepository{

    Page<SelectBlogInfoDTO> findAll(@NotNull BlogSearchCondition condition, @NotNull Pageable pageable);

    List<SelectBlogInfoDTO> findAll(@NotNull BlogSearchCondition condition);
}
