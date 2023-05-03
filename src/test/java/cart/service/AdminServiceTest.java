package cart.service;

import cart.controller.dto.ProductCreationRequest;
import cart.controller.dto.ProductUpdateRequest;
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
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ProductService productService;

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
        adminService.save(productCreationRequest);

        final List<ProductDto> productDtos = productService.getAll();
        assertAll(
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("땡칠"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("asdf"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(100L)
        );
    }


    @Test
    void delete() {
        final ProductCreationRequest productCreationRequest = new ProductCreationRequest("땡칠", "asdf", 100L);
        adminService.save(productCreationRequest);

        adminService.delete(1L);

        final List<ProductDto> productDtos = productService.getAll();

        assertThat(productDtos.size()).isEqualTo(0);
    }

    @Test
    void update() {
        final ProductCreationRequest productCreationRequest = new ProductCreationRequest("땡칠", "asdf", 100L);
        adminService.save(productCreationRequest);

        final ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(1L, "비버", "VERY_BIG_IMAGE", 10000L);
        adminService.update(productUpdateRequest);

        final List<ProductDto> productDtos = productService.getAll();

        assertAll(
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("비버"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("VERY_BIG_IMAGE"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(10000L)
        );
    }

    @Test
    void getAll() {
        final ProductCreationRequest productCreationRequest1 = new ProductCreationRequest("비버", "SMALL_IMAGE", 100L);
        final ProductCreationRequest productCreationRequest2 = new ProductCreationRequest("땡칠", "asdf", 100L);

        adminService.save(productCreationRequest1);
        adminService.save(productCreationRequest2);

        final List<ProductDto> productDtos = productService.getAll();

        assertAll(
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("비버"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("SMALL_IMAGE"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(100L),
                () -> assertThat(productDtos.get(1).getName()).isEqualTo("땡칠"),
                () -> assertThat(productDtos.get(1).getImage()).isEqualTo("asdf"),
                () -> assertThat(productDtos.get(1).getPrice()).isEqualTo(100L)
        );
    }
}
