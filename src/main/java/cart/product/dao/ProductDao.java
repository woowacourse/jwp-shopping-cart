package cart.product.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cart.product.domain.Product;

@Component
public class ProductDao {
	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
		Product product = new Product(
			resultSet.getLong("id"),
			resultSet.getString("name"),
			resultSet.getString("image"),
			resultSet.getInt("price"),
			resultSet.getTimestamp("created_at").toLocalDateTime(),
			resultSet.getTimestamp("updated_at").toLocalDateTime()
		);
		return product;
	};

	public ProductDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Product> findAll() {
		String sql = "SELECT * FROM PRODUCT";

		return jdbcTemplate.query(sql, productRowMapper);
	}
}
