package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDao productDao;

    @Test
    void 상품을_저장한다() {
        // given
        final ProductSaveRequestDto request = new ProductSaveRequestDto("허브티", "tea.jpg", 1000L);

        // when
        final Long id = productService.save(request);

        // then
        final List<Product> result = productDao.findAll();
        assertAll(
                () -> assertThat(id).isPositive(),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    void 모든_상품을_조회한다() {
        // given
        final ProductSaveRequestDto request1 = new ProductSaveRequestDto("허브티", "tea.jpg", 99L);
        final ProductSaveRequestDto request2 = new ProductSaveRequestDto("블랙켓티", "bk_tea.jpg", 100L);
        final Long id1 = productService.save(request1);
        final Long id2 = productService.save(request2);

        // when
        final List<ProductDto> result = productService.findAll();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(List.of(
                new ProductDto(id1, request1.getName(), request1.getImage(), request1.getPrice()),
                new ProductDto(id2, request2.getName(), request2.getImage(), request2.getPrice())
        ));
    }

    @Test
    void 상품을_수정한다() {
        // given
        final ProductSaveRequestDto product = new ProductSaveRequestDto("허브티", "tea.jpg", 99L);
        final Long id = productService.save(product);
        final ProductUpdateRequestDto request = new ProductUpdateRequestDto("블랙캣", "cat.jpg", 100L);

        // when
        productService.update(id, request);

        // then
        final Product result = productDao.findAll().get(0);
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(id),
                () -> assertThat(result.getName()).isEqualTo("블랙캣"),
                () -> assertThat(result.getImage()).isEqualTo("cat.jpg"),
                () -> assertThat(result.getPrice()).isEqualTo(100L)
        );
    }
}
