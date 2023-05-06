package cart.service.product;

import cart.domain.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static cart.fixture.ProductRequestFixture.TEST_CREATION_MEMBER1;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductDeleteServiceTest {
    @Autowired
    private ProductDeleteService productDeleteService;
    @Autowired
    private ProductReadService productReadService;

    @Autowired
    private ProductCreateService createService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    private void clear() {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.update("TRUNCATE TABLE product");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
    }

    @Test
    void delete() {
        createService.save(TEST_CREATION_MEMBER1);

        productDeleteService.delete(1L);

        final List<ProductDto> productDtos = productReadService.getAll();

        assertThat(productDtos.size()).isEqualTo(0);
    }

}