package cart.dao;

import cart.entity.CategoryEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CategoryEntity> findAll() {
        final String sql = "SELECT id, name FROM category";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new CategoryEntity(rs.getLong("id"), rs.getString("name"))
        );
    }

    public List<CategoryEntity> findAllInIds(final List<Long> categoryIds) {
        final String inSql = String.join(",", Collections.nCopies(categoryIds.size(), "?"));
        final String sql = String.format("SELECT id, name FROM category WHERE id IN (%s)", inSql);
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new CategoryEntity(rs.getLong("id"), rs.getString("name")),
                categoryIds.toArray()
        );
    }
}
