package cart.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cart.domain.Product;
import cart.service.dto.ProductUpdateRequest;

@Repository
public class ProductRepository {
	private static final int UPDATED_COUNT = 1;
	private static final int DELETED_COUNT = 1;
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Product> productRowMapper =

		(resultSet, rowNum) ->
			new Product(
				resultSet.getLong("id"),
				resultSet.getString("name"),
				resultSet.getDouble("price"),
				resultSet.getString("image")
			);

	public ProductRepository(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Product> findAll() {
		final String sql = "SELECT * FROM products";
		return jdbcTemplate.query(sql, productRowMapper);
	}

	public Optional<Product> findByProductId(final long productId) {
		final String sql = "SELECT * FROM products WHERE id = ?";
		final Product findProduct = jdbcTemplate.queryForObject(sql, productRowMapper, productId);

		return Optional.ofNullable(findProduct);
	}

	public long save(final ProductUpdateRequest request) {
		final String sql = "INSERT INTO products(name, price, image) VALUES(?, ?, ?)";
		final KeyHolder key = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			final PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
			ps.setString(1, request.getName());
			ps.setDouble(2, request.getPrice());
			ps.setString(3, request.getImage());
			return ps;
		}, key);

		return key.getKey().longValue();
	}

	public boolean deleteByProductId(final long productId) {
		final String sql = "DELETE FROM products WHERE id = ?";
		final int deleteCount = jdbcTemplate.update(sql, productId);

		return deleteCount == DELETED_COUNT;
	}

	public long updateByProductId(final long productId, final ProductUpdateRequest request) {
		final String updateSql = "UPDATE products SET name = ?, price = ?, image = ? WHERE id = ?";
		final int updateCount = jdbcTemplate.update(updateSql, request.getName(), request.getPrice(),
			request.getImage(),
			productId);

		if (updateCount != UPDATED_COUNT) {
			throw new IllegalStateException("상품 갱신 도충 오류가 발생하여 실패하였습니다.");
		}

		return productId;
	}

	public void clear(){
		final String clearSql = "DELETE FROM products";
		final int clear = jdbcTemplate.update(clearSql);
	}
}
