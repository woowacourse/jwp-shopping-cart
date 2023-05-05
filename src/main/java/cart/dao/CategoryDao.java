package cart.dao;

import cart.entity.category.CategoryEntity;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao {

    private static final RowMapper<CategoryEntity> CATEGORY_ENTITY_ROW_MAPPER = (rs, rowNum) -> new CategoryEntity(
        rs.getLong("id"),
        rs.getString("name")
    );

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CategoryEntity> findAllInId(final List<Long> categoryIds) {
        final String inSql = String.join(",", Collections.nCopies(categoryIds.size(), "?"));
        final String sql = String.format("SELECT * FROM category WHERE id IN (%s)", inSql);

        return jdbcTemplate.query(sql, CATEGORY_ENTITY_ROW_MAPPER, categoryIds.toArray());
    }

    public List<CategoryEntity> findAll() {
        final String sql = "SELECT * FROM category";

        return jdbcTemplate.query(sql, CATEGORY_ENTITY_ROW_MAPPER);
    }
}
