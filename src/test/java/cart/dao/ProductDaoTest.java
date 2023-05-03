package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.ProductDto;
import cart.dto.ProductRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ProductDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);

        productRequest = new ProductRequest(
                "치킨",
                "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg",
                10000
        );
    }

    @DisplayName("데이터 저장 테스트")
    @Test
    void Should_Success_When_Save() {
        productDao.save(productRequest);
        ProductDto product = productDao.findAll().get(0);

        assertAll(
                () -> assertThat(product.getName()).isEqualTo("치킨"),
                () -> assertThat(product.getPrice()).isEqualTo(10000),
                () -> assertThat(product.getImgUrl()).isEqualTo(
                        "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg")
        );
    }

    @DisplayName("데이터 변경 테스트")
    @Test
    void Should_Success_When_Update() {
        ProductRequest productRequest2 = new ProductRequest(
                "토리",
                "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg",
                20000
        );

        int id = productDao.save(productRequest);
        productDao.update(productRequest2, id);
        ProductDto product = productDao.findAll().get(0);

        assertAll(
                () -> assertThat(product.getName()).isEqualTo("토리"),
                () -> assertThat(product.getPrice()).isEqualTo(20000),
                () -> assertThat(product.getImgUrl()).isEqualTo(
                        "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg")
        );
    }

    @DisplayName("데이터 삭제 테스트")
    @Test
    void Should_Success_When_Delete() {
        int id = productDao.save(productRequest);
        productDao.delete(id);
        List<ProductDto> productDtoList = productDao.findAll();

        assertThat(productDtoList).hasSize(0);
    }
}
