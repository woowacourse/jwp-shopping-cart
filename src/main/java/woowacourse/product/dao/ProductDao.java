package woowacourse.product.dao;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.product.domain.Price;
import woowacourse.product.domain.Product;
import woowacourse.product.domain.Stock;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ProductDao(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        final SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", product.getName())
            .addValue("price", product.getPrice().getValue())
            .addValue("stock", product.getStock().getValue())
            .addValue("imageURL", product.getImageURL());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<Product> findAll() {
        final String sql = "SELECT id, name, price, stock, imageURL FROM product";
        return jdbcTemplate.query(sql, rowMapper());
    }

    public Optional<Product> findById(final Long id) {
        final String sql = "SELECT id, name, price, stock, imageURL FROM product WHERE id = :id";
        final SqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, rowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Product> rowMapper() {
        return (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            new Price(rs.getInt("price")),
            new Stock(rs.getInt("stock")),
            rs.getString("imageURL")
        );
    }

    public void deleteById(final Long id) {
        final String sql = "DELETE FROM product WHERE id = :id";
        final SqlParameterSource params = new MapSqlParameterSource("id", id);

        jdbcTemplate.update(sql, params);
    }
}
