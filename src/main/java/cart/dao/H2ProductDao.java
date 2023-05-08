package cart.dao;

import cart.entity.ProductEntity;
import cart.exception.InternalServerException;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class H2ProductDao implements ProductDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterjdbcTemplate;

    public H2ProductDao(final NamedParameterJdbcTemplate namedParameterjdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterjdbcTemplate.getJdbcTemplate())
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
        this.namedParameterjdbcTemplate = namedParameterjdbcTemplate;
    }

    @Override
    public Long save(final ProductEntity productEntity) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "select * from product";

        return namedParameterjdbcTemplate.query(sql, getRowMapper()
        );
    }

    private static RowMapper<ProductEntity> getRowMapper() {
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
        namedParameterjdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void delete(final long id) {
        try{
            final String sql = "delete from product where id=?";
            namedParameterjdbcTemplate.getJdbcOperations().update(sql, id);
        } catch (DataIntegrityViolationException exception) {
            throw new InternalServerException("이미 상품이 장바구니에 담겨 삭제할 수 없습니다.");
        } catch (EmptyResultDataAccessException exception) {
            throw new InternalServerException("상품이 존재하지 않습니다.");
        }
    }

    @Override
    public Optional<ProductEntity> findById(final long id) {
        final String sql = "select * from product where id=?";
        return namedParameterjdbcTemplate.getJdbcTemplate()
            .query(sql, getRowMapper(), id).stream()
            .findAny();
    }
}
