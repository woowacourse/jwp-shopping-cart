package woowacourse.shoppingcart.dao;

import static org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import woowacourse.shoppingcart.domain.CartItem;

@Repository
public class CartItemDao {

	private final SimpleJdbcInsert jdbcInsert;
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final ProductDao productDao;

	public CartItemDao(DataSource dataSource, ProductDao productDao) {
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("cart_item")
			.usingGeneratedKeyColumns("id");
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.productDao = productDao;
	}

	public CartItem save(Long customerId, CartItem cartItem) {
		long cartItemId = jdbcInsert.executeAndReturnKey(
				new BeanPropertySqlParameterSource(new CartItemDto(
					customerId,
					cartItem.getProductId(),
					cartItem.getQuantity())))
			.longValue();
		return cartItem.createWithId(cartItemId);
	}

	public List<CartItem> findByCustomerId(Long customerId) {
		String sql = "select * from cart_item where customer_id = :customerId";
		return jdbcTemplate.query(sql,
			Map.of("customerId", customerId),
			getCartItemMapper()
		);
	}

	private RowMapper<CartItem> getCartItemMapper() {
		return (rs, rowNum) -> new CartItem(
			rs.getLong("id"),
			productDao.findById(rs.getLong("product_id")),
			rs.getInt("quantity")
		);
	}

	public void update(CartItem cartItem) {
		String sql = "update cart_item set "
			+ "quantity = :quantity";
		if (jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(cartItem)) == 0) {
			throw new NoSuchElementException("수정하려는 장바구니 상품이 없습니다.");
		}
	}

	public void deleteAll(List<Long> cartItemIds) {
		String sql = "delete from cart_item where id = :id";
		jdbcTemplate.batchUpdate(sql, createBatch(cartItemIds.stream()
			.map(id -> Map.of("id", id))
			.collect(Collectors.toList()))
		);
	}

	@RequiredArgsConstructor
	@Getter
	private static class CartItemDto {
		private final Long customerId;
		private final Long productId;
		private final Integer quantity;
	}
}
