package cart.domain.product.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.domain.product.entity.Product;
import java.util.List;
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
        final ProductDao productDao = new ProductDao(jdbcTemplate);
        final Product givenProduct = new Product(null, "연필", 1000, "imageUrl", null, null);

        //when
        final Product savedProduct = productDao.add(givenProduct);

        //then
        assertThat(savedProduct.getId()).isEqualTo(1);
        assertThat(savedProduct.getName()).isEqualTo(givenProduct.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(givenProduct.getPrice());
        assertThat(savedProduct.getImageUrl()).isEqualTo(givenProduct.getImageUrl());
        assertThat(savedProduct.getCreatedAt()).isNotNull();
        assertThat(savedProduct.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("db에서 모든 상품을 조회한다.")
    public void testFindAll() {
        //given
        final ProductDao productDao = new ProductDao(jdbcTemplate);
        final Product givenProduct1 = new Product(null, "연필", 1000, "imageUrl1", null, null);
        final Product givenProduct2 = new Product(null, "지우개", 2000, "imageUrl2", null, null);
        final Product savedProduct1 = productDao.add(givenProduct1);
        final Product savedProduct2 = productDao.add(givenProduct2);

        //when
        final List<Product> result = productDao.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(savedProduct1.getId());
        assertThat(result.get(1).getId()).isEqualTo(savedProduct2.getId());
    }

    @Test
    @DisplayName("db에서 상품을 수정한다.")
    public void testUpdate() {
        //given
        final ProductDao productDao = new ProductDao(jdbcTemplate);
        final Product givenProduct = new Product(null, "연필", 1000, "imageUrl1", null, null);
        final Product savedProduct = productDao.add(givenProduct);
        final Product updateProduct = new Product(savedProduct.getId(), "지우개", 2000, "imageUrl2",
            null, null);

        //when
        int result = productDao.update(updateProduct);

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("db에서 상품을 삭제한다.")
    public void testDelete() {
        //given
        final ProductDao productDao = new ProductDao(jdbcTemplate);
        final Product givenProduct = new Product(null, "연필", 1000, "imageUrl1", null, null);
        final Product savedProduct = productDao.add(givenProduct);

        //when
        int result = productDao.delete(savedProduct.getId());

        //then
        assertThat(result).isEqualTo(1);
    }
}
