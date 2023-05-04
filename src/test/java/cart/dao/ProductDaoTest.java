package cart.dao;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.convertor.ProductEntityConvertor;
import cart.database.dao.ProductDao;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
class ProductDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private ProductEntityConvertor productEntityConvertor = new ProductEntityConvertor();
	private ProductDao productDao;

	@BeforeEach
	void setUp () {
		this.productDao = new ProductDao(jdbcTemplate);
	}

	@DisplayName("상품 생성 쿼리")
	@Test
	void create () {
		// given
		ProductCreateRequest request = new ProductCreateRequest("product", 5000, "image url");

		// when
		productDao.create(productEntityConvertor.dtoToEntity(request));

		// then
		List<ProductEntity> responses = productDao.findAll();
		assertAll(
				() -> assertThat(responses).hasSize(1),
				() -> assertThat(responses.get(0).getName()).isEqualTo("product"),
				() -> assertThat(responses.get(0).getPrice()).isEqualTo(5000)
		);
	}

	@DisplayName("상품 전체 조회 쿼리")
	@Test
	void findAll () {
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
				() -> assertThat(responses).hasSize(3),
				() -> assertThat(responses).extracting(ProductEntity::getName)
						.contains("product1", "product2", "product3"),
				() -> assertThat(responses).extracting(ProductEntity::getPrice)
						.contains(5000, 6000, 7000)
		);
	}

	@DisplayName("상품 수정 쿼리")
	@Test
	void updateById () {
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

	private Long getResponseId () {
		List<ProductEntity> responses = productDao.findAll();
		ProductEntity response = responses.get(0);
		return response.getId();
	}


	@DisplayName("상품 삭제 쿼리")
	@Test
	void deleteById () {
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
	void create32OverNameLengthFail () {
		// given
		final String name = "012345678901234567890123456789012345";
		ProductCreateRequest request = new ProductCreateRequest(name, 5000, "image url");

		// when

		// then
		assertThrows(ConstraintViolationException.class, () -> productDao.create(productEntityConvertor.dtoToEntity(request)));
	}
}
