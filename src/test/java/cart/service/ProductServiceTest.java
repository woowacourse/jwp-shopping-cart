package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cart.dto.ProductResponse;
import cart.dto.ProductSaveRequest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("저장된 결과를 반환한다.")
    void saveAndFindTest() {
        saveProduct("치킨", 10000);

        final List<ProductResponse> result = productService.findAll();
        Assertions.assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(10000),
                () -> assertThat(result.get(0).getImgUrl()).isEqualTo("img")
        );
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void delete() {
        // given
        final Long id = saveProduct("샐러드", 20000);

        // when
        productService.delete(id);

        // then
        final List<ProductResponse> results = productService.findAll();
        assertThat(results).isEmpty();
    }

    private Long saveProduct(final String name, final int price) {
        final ProductSaveRequest request = new ProductSaveRequest(name, price, "img");
        return productService.save(request);
    }
}
