package cart.dao;

import cart.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2ProductDao implements ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ProductDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final ProductEntity productEntity) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "select * from product";

        return jdbcTemplate.query(sql, rowMapper()
        );
    }

    @Override
    public Optional<ProductEntity> findById(final Long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.getJdbcOperations().queryForObject(sql, rowMapper(), id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<ProductEntity> rowMapper() {
        return (resultSet, count) ->
                new ProductEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("price")
                );
    }

    @Override
    public void update(final ProductEntity productEntity) {
        final String sql = "update product set name = :name, image_url = :imageUrl, price = :price where id = :id";
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void delete(final Long id) {
        final String sql = "delete from product where id=?";
        jdbcTemplate.getJdbcOperations().update(sql, id);
    }
}
