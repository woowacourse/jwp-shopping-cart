package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
    );

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("image_Url", product.getImageUrl());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public Optional<Product> findById(final Long id) {
        String query = "SELECT id, name, price, image_Url FROM product WHERE id=:id";
        SqlParameterSource nameParameters = new MapSqlParameterSource("id", id);

        try {
            Product product = template.queryForObject(query, nameParameters, PRODUCT_ROW_MAPPER);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findAll() {
        String query = "SELECT id, name, price, image_Url FROM product";
        return template.query(query, PRODUCT_ROW_MAPPER);
    }

    public boolean existByName(final String name) {
        String query = "SELECT EXISTS (SELECT * FROM product WHERE name=:name)";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("name", name);
        int count = template.queryForObject(query, nameParameters, Integer.class);
        return count != 0;
    }

    public List<Product> findByIds(final List<Long> ids) {
        String productIds = ids.stream()
                .map(it -> String.valueOf(it))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "SELECT * FROM product WHERE id in " + productIds;
        return template.query(sql, PRODUCT_ROW_MAPPER);
    }
}
