package cart.dao.product;

import cart.domain.TestFixture;
import cart.domain.product.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({ProductRepositoryImpl.class, ProductDao.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepositoryImpl productRepository;

    @DisplayName("상품 전체 조회 테스트")
    @Test
    void findAll() {
        productRepository.save(TestFixture.CHICKEN);
        productRepository.save(TestFixture.PIZZA);

        List<Product> allProducts = productRepository.findAll();

        assertThat(allProducts).hasSize(2);
        assertThat(allProducts).extractingResultOf("getName")
                .contains("Chicken", "Pizza");
    }

    @DisplayName("상품 저장 테스트")
    @Test
    void insert() {
        Product pizza = TestFixture.PIZZA;

        productRepository.save(pizza);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts).extractingResultOf("getName")
                .contains("Pizza");
    }

    @DisplayName("단일 상품 삭제 테스트")
    @Test
    void deleteById() {
        Product pizza = TestFixture.PIZZA;
        Long savedId = productRepository.save(pizza);
        assertThat(productRepository.findAll()).hasSize(1);

        productRepository.deleteById(savedId);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(0);
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void update() {
        Product pizza = TestFixture.PIZZA;
        Long savedId = productRepository.save(pizza);

        Product updatedProduct = new Product(
                ProductName.from("Chicken"),
                ProductPrice.from(20_000),
                ProductCategory.FOOD,
                ImageUrl.from("chicken.com"),
                savedId
        );

        productRepository.update(updatedProduct);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        Product savedProduct = allProducts.get(0);
        assertThat(savedProduct.getId()).isEqualTo(savedId);
        assertThat(savedProduct.getName()).isEqualTo("Chicken");
        assertThat(savedProduct.getPrice()).isEqualTo(BigDecimal.valueOf(20_000));
        assertThat(savedProduct.getCategory()).isEqualTo(ProductCategory.FOOD);
        assertThat(savedProduct.getImageUrl()).isEqualTo("chicken.com");
    }
}
