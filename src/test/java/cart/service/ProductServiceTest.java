package cart.service;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import cart.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void findAllTest() {
        ProductEntity entity1 = new ProductEntity(1L, "name1", "url1.com", 1000);
        ProductEntity entity2 = new ProductEntity(2L, "name2", "url2.com", 2000);
        when(productRepository.findAll()).thenReturn(List.of(entity1, entity2));

        ProductDto expectDto1 = ProductDto.fromEntity(entity1);
        ProductDto expectDto2 = ProductDto.fromEntity(entity2);
        assertThat(productService.findAll()).isEqualTo(List.of(expectDto1, expectDto2));
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addTest() {
        String name = "name1";
        String imgUrl = "url1.com";
        int price = 1000;
        ProductRequestDto request = new ProductRequestDto(name, imgUrl, price);
        ProductEntity entity = new ProductEntity(1L, name, imgUrl, price);
        when(productRepository.save(any(Product.class))).thenReturn(entity);

        ProductDto expectDto = ProductDto.fromEntity(entity);
        assertThat(productService.add(request)).isEqualTo(expectDto);
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 수정한다.")
    void updateByIdTest() {
        Long id = 1L;
        String newName = "name2";
        String newImgUrl = "url2.com";
        int newPrice = 2000;
        ProductRequestDto request = new ProductRequestDto(newName, newImgUrl, newPrice);
        doNothing().when(productRepository).update(any(ProductEntity.class));

        ProductDto expectDto = ProductDto.fromEntity(new ProductEntity(id, newName, newImgUrl, newPrice));
        assertThat(productService.updateById(request, id)).isEqualTo(expectDto);
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 삭제한다.")
    void deleteByIdTest() {
        Long id = 1L;
        doNothing().when(productRepository).deleteById(any(Long.class));

        assertThatNoException().isThrownBy(() -> productService.deleteById(id));
    }
}