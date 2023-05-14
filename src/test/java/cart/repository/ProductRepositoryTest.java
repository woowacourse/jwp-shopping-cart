package cart.repository;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entity.ProductEntity;
import cart.excpetion.product.DuplicateProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductRepository productRepository;


    @DisplayName("등록하는 상품명이 이미 존재한다면 예가 발생한다.")
    @Test
    void create_invalid() {
        //given
        given(productDao.exitingProductName(anyString()))
                .willReturn(true);

        //when
        final Product product = new Product("name", "image.jpg", 1);

        //then
        assertThatThrownBy(() -> productRepository.create(product))
                .isInstanceOf(DuplicateProductException.class);
    }

    @DisplayName("상품명이 존재하지 않았다면 생성한다")
    @Test
    void create() {
        //given
        given(productDao.exitingProductName(anyString()))
                .willReturn(false);
        given(productDao.create(any()))
                .willReturn(1);
        final Product product = new Product("name", "image.jpg", 1);

        //when
        productRepository.create(product);

        //then
        verify(productDao, times(1)).create(any());
    }

    @DisplayName("존재하는 모든 상품들을 가조온다")
    @Test
    void findAll() {
        //given
        given(productDao.findAll())
                .willReturn(List.of(
                                new ProductEntity(1, "name1", "image1", 1),
                                new ProductEntity(2, "name2", "image2", 2)
                        )
                );

        //when
        final List<Product> allProducts = productRepository.findAll();
        final Product firstProduct = allProducts.get(0);

        //then
        assertAll(
                () -> assertThat(allProducts).hasSize(2),
                () -> assertThat(firstProduct.getId()).isEqualTo(1),
                () -> assertThat(firstProduct.getName()).isEqualTo("name1"),
                () -> assertThat(firstProduct.getImage()).isEqualTo("image1"),
                () -> assertThat(firstProduct.getPrice()).isEqualTo(1)

        );
    }

    @DisplayName("상품이 존재하지 않는다면 빈 Optional 을 반환한다.")
    @Test
    void findBy_returnOptionEmpty() {
        //given
        given(productDao.findBy(anyInt()))
                .willReturn(Optional.empty());

        //when
        final Optional<Product> findProduct = productRepository.findBy(1);

        //then
        assertThat(findProduct).isNotPresent();
    }

    @DisplayName("상품이 존재한다면 Optional 에 Product 를 넣어서 반환한다")
    @Test
    void findBy_returnProductOptional() {
        //given
        given(productDao.findBy(anyInt()))
                .willReturn(Optional.of(new ProductEntity(1, "name", "image", 1)));

        //when
        final Optional<Product> findProduct = productRepository
                .findBy(1);

        //then
        assertThat(findProduct).isPresent();
    }
}
