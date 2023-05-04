package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.CategoryEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

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
    @DisplayName("모든 카테고리를 조회한다.")
    void findAll() {
        final List<CategoryEntity> result = categoryDao.findAll();

        assertThat(result).hasSize(10);
    }

    @Test
    @DisplayName("카테고리 ID 목록으로 카테고리를 모두 조회한다.")
    void getCategories() {
        final List<Long> ids = List.of(1L, 4L, 6L);

        final List<CategoryEntity> result = categoryDao.findAllInIds(ids);

        assertThat(result).map(CategoryEntity::getName)
                .containsExactly("카페", "일식", "치킨");
    }
}
