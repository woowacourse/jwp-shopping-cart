package cart.service;

import cart.dao.CategoryDao;
import cart.dto.response.CategoryResponseDto;
import cart.entity.CategoryEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryDao categoryDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("모든 카테고리를 조회한다.")
    void findAll() throws JsonProcessingException {
        final CategoryEntity all = new CategoryEntity(1L, "전체");
        final CategoryEntity korean = new CategoryEntity(2L, "한식");
        given(categoryDao.findAll()).willReturn(List.of(all, korean));

        final List<CategoryResponseDto> result = categoryService.findCategories();

        final List<CategoryResponseDto> expected = List.of(
                CategoryResponseDto.from(all),
                CategoryResponseDto.from(korean)

        );
        assertThat(objectMapper.writeValueAsString(result)).isEqualTo(objectMapper.writeValueAsString(expected));
    }
}
