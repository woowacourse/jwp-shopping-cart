package cart.product.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public List<Product> findAll() {
		String sql = "SELECT * FROM PRODUCT";

		return jdbcTemplate.query(sql, productRowMapper);
	}

	@Transactional
	public long save(final Product product) {
		final String sql = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
			ps.setString(1, product.getName());
			ps.setInt(2, product.getPrice());
			ps.setString(3, product.getImage());
			return ps;
		}, keyHolder);

		return (long)keyHolder.getKeys().get("id");
	}

	public long updateById(Long id, Product product) {
		final String sql = "UPDATE product SET name = ?, price = ?, image = ? WHERE id = ?";
		return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImage(), id);
	}
}
