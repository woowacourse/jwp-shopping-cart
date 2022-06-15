package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.exception.ErrorCode;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.exception.InvalidProductException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.sql.DataSource;

@Repository
public class ProductDao {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ProductDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public Product findById(final Long id) {
		try {
			final String sql = "select id, name, price, image_url from product where id = :id";
			return jdbcTemplate.queryForObject(sql, Map.of("id", id), getProductMapper());
		} catch (EmptyResultDataAccessException exception) {
			throw new InvalidProductException(ErrorCode.PRODUCT_NOT_FOUND, "해당 id의 상품이 존재하지 않습니다.");
		}
	}

	public List<Product> findAll() {
		final String query = "select id, name, price, image_url from product";
		return jdbcTemplate.query(query, getProductMapper());
	}

	private RowMapper<Product> getProductMapper() {
		return (resultSet, rowNumber) -> new Product(
			resultSet.getLong("id"),
			resultSet.getString("name"),
			resultSet.getInt("price"),
			resultSet.getString("image_url")
		);
	}
}
