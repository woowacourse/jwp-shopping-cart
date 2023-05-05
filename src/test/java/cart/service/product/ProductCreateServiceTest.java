package cart.service.product;

import cart.controller.dto.request.ProductCreationRequest;
import cart.domain.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ProductCreateServiceTest {

    @Autowired
    private ProductCreateService productCreateService;
    @Autowired
    private ProductReadService productReadService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    private void clear() {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.update("TRUNCATE TABLE product");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
    }

    @Test
    void add() {
        final ProductCreationRequest productCreationRequest = new ProductCreationRequest("땡칠", "asdf", 100L);
        productCreateService.save(productCreationRequest);

        final List<ProductDto> productDtos = productReadService.getAll();
        assertAll(
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("땡칠"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("asdf"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(100L)
        );
    }
}