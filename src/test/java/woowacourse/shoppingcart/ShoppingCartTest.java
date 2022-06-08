package woowacourse.shoppingcart;

import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {"file:src/test/resources/test_schema.sql", "file:src/test/resources/test_product_data.sql"})
public class ShoppingCartTest {
}
