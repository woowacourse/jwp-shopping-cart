package cart.database.dao;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.convertor.ProductEntityConvertor;
import cart.entity.ProductEntity;
import cart.validator.CostumeDefaultValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CostumeDefaultValidator costumeDefaultValidator = new CostumeDefaultValidator();
    private ProductEntityConvertor productEntityConvertor = new ProductEntityConvertor();
    private ProductDao productDao;
    private String name;
    private String imageUrl;
    private int price;

    @BeforeEach
    void setUp() {
        this.productDao = new ProductDao(jdbcTemplate, costumeDefaultValidator);

        name = "temp";
        imageUrl = "temp_img";
        price = 1000;

        jdbcTemplate.update(
                "INSERT INTO PRODUCT (NAME, IMAGE_URL, PRICE) VALUES (?, ?, ?)",
                name, imageUrl, price
        );
    }

    @DisplayName("상품 생성 쿼리")
    @Test
    void create() {
        // given
        ProductCreateRequest request = new ProductCreateRequest("product", 5000, "image url");

        // when
        productDao.create(productEntityConvertor.dtoToEntity(request));

        // then
        List<ProductEntity> responses = productDao.findAll();
        assertAll(
                () -> assertThat(responses).hasSize(2),
                () -> assertThat(responses.get(1).getName()).isEqualTo("product"),
                () -> assertThat(responses.get(1).getPrice()).isEqualTo(5000)
        );
    }

    @DisplayName("상품 전체 조회 쿼리")
    @Test
    void findAll() {
        // given
        ProductCreateRequest request1 = new ProductCreateRequest("product1", 5000, "image url");
        ProductCreateRequest request2 = new ProductCreateRequest("product2", 6000, "image url");
        ProductCreateRequest request3 = new ProductCreateRequest("product3", 7000, "image url");
        productDao.create(productEntityConvertor.dtoToEntity(request1));
        productDao.create(productEntityConvertor.dtoToEntity(request2));
        productDao.create(productEntityConvertor.dtoToEntity(request3));

        // when
        List<ProductEntity> responses = productDao.findAll();

        // then
        assertAll(
                () -> assertThat(responses).hasSize(4),
                () -> assertThat(responses).extracting(ProductEntity::getName)
                        .contains("temp", "product1", "product2", "product3"),
                () -> assertThat(responses).extracting(ProductEntity::getPrice)
                        .contains(1000, 5000, 6000, 7000)
        );
    }

    @DisplayName("상품 수정 쿼리")
    @Test
    void updateById() {
        // given
        ProductCreateRequest createRequest = new ProductCreateRequest("product1", 5000, "image url");
        ProductUpdateRequest updateRequest = new ProductUpdateRequest("update product", 2000, "img");
        productDao.create(productEntityConvertor.dtoToEntity(createRequest));

        // when
        productDao.updateById(getResponseId(), productEntityConvertor.dtoToEntity(updateRequest));

        ProductEntity response = productDao.findAll().get(0);

        // then
        assertAll(
                () -> assertThat(response.getName()).isEqualTo("update product"),
                () -> assertThat(response.getPrice()).isEqualTo(2000)
        );
    }

    private Long getResponseId() {
        List<ProductEntity> responses = productDao.findAll();
        ProductEntity response = responses.get(0);
        return response.getId();
    }


    @DisplayName("상품 삭제 쿼리")
    @Test
    void deleteById() {
        // given
        ProductCreateRequest createRequest1 = new ProductCreateRequest("product1", 5000, "image url");
        ProductCreateRequest createRequest2 = new ProductCreateRequest("product1", 5000, "image url");
        ProductCreateRequest createRequest3 = new ProductCreateRequest("product1", 5000, "image url");
        productDao.create(productEntityConvertor.dtoToEntity(createRequest1));
        productDao.create(productEntityConvertor.dtoToEntity(createRequest2));
        productDao.create(productEntityConvertor.dtoToEntity(createRequest3));

        // when
        int beforeSize = productDao.findAll().size();
        productDao.deleteById(getResponseId());
        int afterSize = productDao.findAll().size();

        // then
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }

    @DisplayName("이름이 32자 넘는 경우 요청 실패")
    @Test
    void create32OverNameLengthFail() {
        // given
        final String name = "012345678901234567890123456789012345";
        ProductCreateRequest request = new ProductCreateRequest(name, 5000, "image url");

        // when

        // then
        assertThrows(ConstraintViolationException.class, () -> productDao.create(productEntityConvertor.dtoToEntity(request)));
    }

    @DisplayName("Product 존재 여부 테스트")
    @Test
    void existById() {
        //given

        //when

        //then
        assertThat(productDao.existById(1L)).isTrue();
    }
}