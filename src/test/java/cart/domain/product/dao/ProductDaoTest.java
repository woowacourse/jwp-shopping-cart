package cart.domain.product.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.domain.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("db에 상품을 추가한다.")
    public void testAdd() {
        //given
        ProductDao productDao = new ProductDao(jdbcTemplate);
        Product givenProduct = new Product(null, "연필", 1000, "imageUrl", null, null);

        //when
        Product savedProduct = productDao.add(givenProduct);

        //then
        assertThat(savedProduct.getId()).isEqualTo(1);
        assertThat(savedProduct.getName()).isEqualTo(givenProduct.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(givenProduct.getPrice());
        assertThat(savedProduct.getImageUrl()).isEqualTo(givenProduct.getImageUrl());
        assertThat(savedProduct.getCreatedAt()).isNotNull();
        assertThat(savedProduct.getUpdatedAt()).isNotNull();
    }

}
