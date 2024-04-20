package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Post;
import uos.mystory.domain.QPost;
import uos.mystory.dto.mapping.select.QSelectCategoryInfoDTO;
import uos.mystory.dto.mapping.select.QSelectPostInfoDTO;
import uos.mystory.dto.mapping.select.SelectPostInfoDTO;
import uos.mystory.repository.condition.PostSearchCondition;

import java.util.List;
import java.util.Optional;

import static uos.mystory.domain.QPost.post;

public interface PostQueryRepository {

    public Page<SelectPostInfoDTO> findAll(@NotNull PostSearchCondition condition, @NotNull Pageable pageable);

    public List<SelectPostInfoDTO> findAll(@NotNull PostSearchCondition condition);

    public Optional<SelectPostInfoDTO> findDtoById(Long postId);
}
