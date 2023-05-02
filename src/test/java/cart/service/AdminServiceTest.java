package cart.service;

import cart.domain.dto.ProductCreationDto;
import cart.domain.dto.ProductDto;
import cart.domain.dto.ProductUpdateDto;
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
        jdbcTemplate.update("TRUNCATE TABLE product");
    }

    @Test
    void add() {
        final ProductCreationDto productCreationDto = new ProductCreationDto("땡칠", "asdf", 100);
        adminService.add(productCreationDto);

        final List<ProductDto> productDtos = productService.getAll();
        assertAll(
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("땡칠"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("asdf"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(100L)
        );
    }


    @Test
    void delete() {
        final ProductCreationDto productCreationDto = new ProductCreationDto("땡칠", "asdf", 100);
        adminService.add(productCreationDto);

        adminService.delete(1);

        final List<ProductDto> productDtos = productService.getAll();

        assertThat(productDtos.size()).isEqualTo(0);
    }

    @Test
    void update() {
        final ProductCreationDto productCreationDto = new ProductCreationDto("땡칠", "asdf", 100);
        adminService.add(productCreationDto);

        final ProductUpdateDto productUpdateDto = new ProductUpdateDto(1, "비버", "VERY_BIG_IMAGE", 10000);
        adminService.update(productUpdateDto);

        final List<ProductDto> productDtos = productService.getAll();

        assertAll(
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("비버"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("VERY_BIG_IMAGE"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(10000L)
        );
    }

    @Test
    void getAll() {
        final ProductCreationDto productCreationDto1 = new ProductCreationDto("비버", "SMALL_IMAGE", 100);
        final ProductCreationDto productCreationDto2 = new ProductCreationDto("땡칠", "asdf", 100);

        adminService.add(productCreationDto1);
        adminService.add(productCreationDto2);

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
