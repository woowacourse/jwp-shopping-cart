package cart.business.service;

import cart.business.domain.product.Product;
import cart.business.domain.product.ProductId;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import cart.business.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    private static Product productFixture;

    @BeforeAll
    static void setup() {
        productFixture = new Product(
                new ProductId(1), new ProductName("dummy"),
                new ProductImage("https://"), new ProductPrice(1));
    }

    @Test
    @DisplayName("동일한 이름을 가진 상품을 create 할 시 예외를 던진다")
    void test_create_exception() {
        // when
        when(productRepository.findByName(any())).thenReturn(Optional.of(productFixture));

        // then
        assertThatThrownBy(() -> productService.create(productFixture))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("동일한 이름을 가진 상품이 없을 경우, create가 잘 수행된다")
    void test_create() {
        // when
        when(productRepository.findByName(any())).thenReturn(Optional.empty());

        // then
        assertThatCode(() -> productService.create(productFixture))
                .doesNotThrowAnyException();
    }


    @Test
    @DisplayName("존재하지 않는 상품을 read 하려고 하면 예외를 던진다")
    void test_read_exception() {
        // when
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> productService.readById(productFixture.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하는 상품에 대해서 read 할 수 있다")
    void test_read() {
        // when
        when(productRepository.findById(any())).thenReturn(Optional.of(productFixture));

        // then
        assertThatCode(() -> productService.readById(productFixture.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대해 update 할 시 예외를 던진다")
    void test_update_exception() {
        // when
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> productService.update(productFixture))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하는 상품에 대해서 update 할 수 있다")
    void test_update() {
        // when
        when(productRepository.findById(any())).thenReturn(Optional.of(productFixture));

        // then
        assertThatCode(() -> productService.update(productFixture))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대해 delete 할 시 예외를 던진다")
    void test_delete_exception() {
        // when
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> productService.delete(productFixture.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하는 상품에 대해서 delete 할 수 있다")
    void test_delete() {
        // when
        when(productRepository.findById(any())).thenReturn(Optional.of(productFixture));

        // then
        assertThatCode(() -> productService.delete(productFixture.getId()))
                .doesNotThrowAnyException();
    }
}
