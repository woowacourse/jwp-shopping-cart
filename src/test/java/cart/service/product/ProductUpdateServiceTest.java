package cart.service.product;

import cart.domain.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static cart.fixture.ProductRequestFixture.TEST_CREATION_MEMBER1;
import static cart.fixture.ProductRequestFixture.TEST_UPDATE_MEMBER1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ProductUpdateServiceTest {
    @Autowired
    private ProductUpdateService productUpdateService;
    @Autowired
    private ProductReadService productReadService;
    @Autowired
    private ProductCreateService productCreateService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    private void clear() {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.update("TRUNCATE TABLE product");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
    }

    @Test
    void update() {
        productCreateService.save(TEST_CREATION_MEMBER1);

        productUpdateService.update(TEST_UPDATE_MEMBER1);

        final List<ProductDto> productDtos = productReadService.getAll();

        assertAll(
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("비버"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("VERY_BIG_IMAGE"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(10000L)
        );
    }

}