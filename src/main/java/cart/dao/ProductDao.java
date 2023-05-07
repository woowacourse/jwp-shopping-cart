package cart.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import cart.dao.dto.ProductDto;

@Component
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Integer insert(final String name, String image, Long price) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("image", image)
                .addValue("price", price);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).intValue();
    }

    public int update(final Integer id, String name, String image, Long price) {
        String sql = "UPDATE product SET name= ?, image= ?, price=? WHERE id= ?";
        return jdbcTemplate.update(
                sql,
                name, image, price,
                id
        );
    }

    public int deleteById(final Integer id) {
        String sql = "delete from product where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Optional<ProductDto> select(final Integer id) {
        return DataAccessExceptionHandler.handleWithOptional(() -> {
            String sql = "select * from product where id = ?";

            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> new ProductDto(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("image"),
                            rs.getLong("price")
                    ),
                    id
            );
        });
    }

    public List<ProductDto> findAll() {
        String sql = "select * from product";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new ProductDto(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("image"),
                                rs.getLong("price")
                        )
        );
    }

    private <T> Optional<T> handleDataAccessExceptionOf(Supplier<T> supplier) {
        try {
            return Optional.ofNullable(supplier.get());
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }
}
