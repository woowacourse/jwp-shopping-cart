package cart.service;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("상품이 삭제되면 장바구니 안의 상품도 삭제된다.")
    @Sql({"/product_dummy_data.sql", "/cart_dummy_data.sql"})
    void delete_cart_item_also_success() {
        // given
        final int id = 1;

        // when
        productService.delete(id);

        // then
        final String productSql = "select * from product";
        final String cartSql = "select * from cart";
        final List<ProductEntity> products = jdbcTemplate.query(productSql,
                (rs, rowNum) -> new ProductEntity(
                        rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getInt("price")
                ));
        final List<CartEntity> cartItems = jdbcTemplate.query(cartSql,
                (rs, rowNum) -> new CartEntity(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("product_id")));

        assertAll(
                () -> assertThat(products).hasSize(2),
                () -> assertThat(
                        products.stream()
                                .map(ProductEntity::getId)
                                .collect(Collectors.toUnmodifiableList()))
                        .doesNotContain(id),

                () -> assertThat(cartItems).hasSize(1),
                () -> assertThat(
                        cartItems.stream()
                                .map(CartEntity::getProductId)
                                .collect(Collectors.toUnmodifiableList()))
                        .doesNotContain(id)
        );
    }
}