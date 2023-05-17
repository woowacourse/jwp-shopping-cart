package cart.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.domain.product.Product;
import cart.domain.product.ProductId;

@Repository
public class ProductRepository {
	private static final int UPDATED_COUNT = 1;
	private static final int DELETED_COUNT = 1;
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	private final RowMapper<Product> productRowMapper =

		(resultSet, rowNum) ->
			new Product(
				ProductId.from(resultSet.getLong("id")),
				resultSet.getString("name"),
				resultSet.getDouble("price"),
				resultSet.getString("image")
			);

	public ProductRepository(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("products")
			.usingGeneratedKeyColumns("id");
	}

	public ProductId insert(final Product product) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(product);
		long id = jdbcInsert.executeAndReturnKey(params).longValue();
		return ProductId.from(id);
	}

	public List<Product> findAll() {
		final String sql = "SELECT * FROM products";
		return jdbcTemplate.query(sql, productRowMapper);
	}

	public Product findByProductId(final ProductId productId) {
		final String sql = "SELECT * FROM products WHERE id = ?";
		final Product product = jdbcTemplate.queryForObject(sql, productRowMapper, productId.getId());
		if (product == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return product;
	}

	public boolean deleteByProductId(final ProductId productId) {
		final String sql = "DELETE FROM products WHERE id = ?";
		final int deleteCount = jdbcTemplate.update(sql, productId.getId());

		return deleteCount == DELETED_COUNT;
	}

	public ProductId updateByProductId(final Product newProduct) {
		final String updateSql = "UPDATE products SET name = ?, price = ?, image = ? WHERE id = ?";
		final int updateCount = jdbcTemplate.update(updateSql, newProduct.getName(), newProduct.getPrice(),
			newProduct.getImage(), newProduct.getId().getId());

		if (updateCount != UPDATED_COUNT) {
			throw new IllegalStateException(String.format("1개 이상의 상품이 수정되었습니다. 수정된 상품 수 : %d", updateCount));
		}

		return newProduct.getId();
	}
}
