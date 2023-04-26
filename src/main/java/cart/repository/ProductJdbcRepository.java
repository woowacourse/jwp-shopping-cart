package cart.repository;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cart.controller.request.ProductCreateRequest;

@Repository
public class ProductJdbcRepository implements ProductRepository {
	private final JdbcTemplate jdbcTemplate;

	public ProductJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public long save(ProductCreateRequest request) {
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
}
