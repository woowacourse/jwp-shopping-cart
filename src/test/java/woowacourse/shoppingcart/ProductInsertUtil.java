package woowacourse.shoppingcart;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import woowacourse.shoppingcart.domain.Product;

@Component
public class ProductInsertUtil {

	private final SimpleJdbcInsert jdbcInsert;

	public ProductInsertUtil(DataSource dataSource) {
		jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("product")
			.usingGeneratedKeyColumns("id");
	}

	public Long insert(String name, int price, String imageUrl) {
		return jdbcInsert.executeAndReturnKey(
			new BeanPropertySqlParameterSource(new Product(name, price, imageUrl))
		).longValue();
	}

	public Product insertAndReturn(String name, int price, String imageUrl) {
		return new Product(insert(name, price, imageUrl), name, price, imageUrl);
	}
}
