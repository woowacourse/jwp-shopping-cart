package cart.dao;

import cart.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CategoryDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CategoryDao categoryDao;

    @BeforeEach
    void setUp() {
        categoryDao = new CategoryDao(jdbcTemplate);
    }

    @Test
    @DisplayName("카테고리 ID 목록으로 저장되어 있는 카테고리를 모두 조회한다.")
    void getCategories() {
        //given
        final List<Long> ids = List.of(1L, 4L, 6L);

        //when
        final List<CategoryEntity> categoryEntities = categoryDao.findAllInId(ids);

        //then
        assertThat(categoryEntities).map(CategoryEntity::getName)
                .containsExactly("카페", "일식", "치킨");
    }
}
