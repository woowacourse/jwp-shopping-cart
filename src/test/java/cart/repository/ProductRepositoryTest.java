package cart.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;

@SpringBootTest
@MockitoSettings
@Sql("classpath:schema.sql")
class ProductRepositoryTest {

    @MockBean
    private ProductDao productDao;

    @Autowired
    private ProductRepository productRepository;

    private final Product product = new Product("item1", 1000, "https://");

    @Test
    void 상품_데이터_삽입() {
        final var expectId = 4L;

        Mockito.when(productDao.insert(any(ProductEntity.class)))
                .thenReturn(expectId);

        final var id = productRepository.insert(product);

        assertThat(id).isEqualTo(expectId);
    }

    @Test
    void 단일_상품_데이터_조회() {
        final var expectName = "expectName";
        final var expectPrice = 1_000;
        final var expectImageUrl = "imageUrl";

        Mockito.when(productDao.findById(anyLong()))
                .thenReturn(new ProductEntity(expectName, expectPrice, expectImageUrl));

        var product = productRepository.findById(1L);

        assertAll(
                () -> assertThat(product.getName()).isEqualTo(expectName),
                () -> assertThat(product.getPrice()).isEqualTo(expectPrice),
                () -> assertThat(product.getImageUrl()).isEqualTo(expectImageUrl)
        );
    }

    @Test
    void 모든_상품_데이터_조회() {
        final var expectSize = 2;

        Mockito.when(productDao.findAll())
                .thenReturn(List.of(
                        new ProductEntity("item1", 1000, "imageUrl1"),
                        new ProductEntity("item2", 2000, "imageUrl2")
                ));

        var products = productRepository.findAll();

        assertThat(products.size()).isEqualTo(expectSize);
    }

    @Test
    void 상품_데이터_수정_성공() {
        assertThatNoException().isThrownBy(
                () -> productRepository.update(1L, new Product("name", 10, "imageUrl"))
        );
    }

    @Test
    void 상품_데이터_삭제() {
        assertThatNoException().isThrownBy(
                () -> productRepository.delete(1L)
        );
    }
}
