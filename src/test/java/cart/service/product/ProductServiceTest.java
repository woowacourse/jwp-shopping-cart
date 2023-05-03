package cart.service.product;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.product.Product;
import cart.service.product.dto.ProductDto;
import cart.service.product.dto.SaveProductDto;
import cart.service.product.dto.UpdateProductDto;

@Transactional
@SpringBootTest
class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void tearDown() {
		final String sql = "DELETE FROM PRODUCT";
		jdbcTemplate.update(sql);
	}

	@Nested
	class CrudTest {

		@Test
		void findAllTest() {
			// when
			final List<ProductDto> productDtos = productService.findAll();

			// then
			assertThat(productDtos).isNotNull();
			assertThat(productDtos).hasSize(0);
		}

		@Test
		void saveTest() {
			//given
			final SaveProductDto saveProductDto = new SaveProductDto("name", "image", 100);

			//when
			final ProductDto result = productService.saveProducts(saveProductDto);

			//then
			assertThat(saveProductDto.getName()).isEqualTo(result.getName());
			assertThat(saveProductDto.getImage()).isEqualTo(result.getImage());
			assertThat(saveProductDto.getPrice()).isEqualTo(result.getPrice());
		}

		@Test
		void updateTest() {
			//given
			final SaveProductDto saveProductDto = new SaveProductDto("name", "image", 100);
			final ProductDto productDto = productService.saveProducts(saveProductDto);
			final Long id = productDto.getId();

			//when
			final UpdateProductDto updateProductDto = new UpdateProductDto(id, "name2", "image2", 1000);
			final ProductDto result = productService.updateProducts(updateProductDto);

			//then
			assertThat(updateProductDto.getName()).isEqualTo(result.getName());
			assertThat(updateProductDto.getImage()).isEqualTo(result.getImage());
			assertThat(updateProductDto.getPrice()).isEqualTo(result.getPrice());
		}

		@Test
		void deleteTest() {
			//given
			final SaveProductDto saveProductDto = new SaveProductDto("name", "image", 100);
			final ProductDto productDto = productService.saveProducts(saveProductDto);
			final Long id = productDto.getId();

			//when
			productService.deleteProductsById(id);

			//then
			assertThatThrownBy(
				() -> jdbcTemplate.queryForObject("SELECT * FROM PRODUCT WHERE id = ?", (rs, rowNum) -> new Product(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("image"),
					rs.getInt("price"),
					rs.getTimestamp("created_at").toLocalDateTime(),
					rs.getTimestamp("updated_at").toLocalDateTime()
				), id))
				.isInstanceOf(EmptyResultDataAccessException.class);
		}
	}

}
