package cart.dao.product;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.product.dto.ProductCreateDTO;
import cart.dao.product.dto.ProductUpdateDTO;
import cart.domain.product.Product;

@Component
public class ProductDao {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> new Product(
		resultSet.getLong("id"),
		resultSet.getString("name"),
		resultSet.getString("image"),
		resultSet.getInt("price"),
		resultSet.getTimestamp("created_at").toLocalDateTime(),
		resultSet.getTimestamp("updated_at").toLocalDateTime()
	);

	public ProductDao(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public List<Product> findAll() {
		final String sql = "SELECT * FROM PRODUCT";

		return jdbcTemplate.query(sql, productRowMapper);
	}

	@Transactional
	public long save(final ProductCreateDTO productCreateDTO) {
		final String sql = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
			ps.setString(1, productCreateDTO.getName());
			ps.setInt(2, productCreateDTO.getPrice());
			ps.setString(3, productCreateDTO.getImage());
			return ps;
		}, keyHolder);

		return (long)Objects.requireNonNull(keyHolder.getKeys()).get("id");
	}

	public Optional<Product> findById(Long id) {
		final String sql = "SELECT * FROM PRODUCT WHERE id = ?";

		try {
			final Product product = jdbcTemplate.queryForObject(sql, productRowMapper, id);
			return Optional.ofNullable(product);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public long updateById(final ProductUpdateDTO productUpdateDTO) {
		final String sql = "UPDATE product SET name = ?, price = ?, image = ? WHERE id = ?";

		return jdbcTemplate.update(sql, productUpdateDTO.getName(), productUpdateDTO.getPrice(),
			productUpdateDTO.getImage(), productUpdateDTO.getId());
	}

	public void deleteById(final Long id) {
		final String sql = "DELETE FROM product where id = ?";

		jdbcTemplate.update(sql, id);
	}
}
