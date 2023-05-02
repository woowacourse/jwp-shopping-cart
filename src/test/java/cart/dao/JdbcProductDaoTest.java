package cart.dao;

import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class JdbcProductDaoTest {

    ProductRequestDto productRequestDto = new ProductRequestDto("케로로", 1000, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
    ProductEntity productEntity = new ProductEntity(productRequestDto.getName(), productRequestDto.getPrice(), productRequestDto.getImage());


    @Autowired
    private JdbcProductDao jdbcProductDao;

    @DisplayName("addProduct 성공 테스트")
    @Test
    void insertProduct() {

        Assertions.assertDoesNotThrow(() -> jdbcProductDao.insertProduct(productEntity));
    }

    @DisplayName("selectAllProducts 성공 테스트")
    @Test
    void selectAllProducts() {
        ProductRequestDto productRequestDto2 = new ProductRequestDto("타마마", 10000, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        ProductEntity productEntity2 = new ProductEntity(productRequestDto2.getName(), productRequestDto2.getPrice(), productRequestDto2.getImage());

        jdbcProductDao.insertProduct(productEntity);
        jdbcProductDao.insertProduct(productEntity2);

        List<ProductEntity> productEntities = jdbcProductDao.selectAllProducts();

        assertAll(
                () -> assertThat(productEntities).hasSize(2),
                () -> assertThat(productEntities).extracting("name", "price", "image")
                        .contains(tuple(productRequestDto.getName(), productRequestDto.getPrice(), productRequestDto.getImage())
                                , tuple(productRequestDto2.getName(), productRequestDto2.getPrice(), productRequestDto2.getImage()))
        );
    }

    @DisplayName("updateProduct 성공 테스트")
    @Test
    void updateProduct() {
        int id = jdbcProductDao.insertProduct(productEntity);
        ProductRequestDto updateProductDto = new ProductRequestDto("기로로", 100, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        ProductEntity updateProductEntity = new ProductEntity(id, updateProductDto.getName(), updateProductDto.getPrice(), updateProductDto.getImage());

        jdbcProductDao.updateProduct(updateProductEntity);

        List<ProductEntity> productEntities = jdbcProductDao.selectAllProducts();

        assertThat(productEntities).extracting("name", "price", "image")
                .contains(tuple(updateProductDto.getName(), updateProductDto.getPrice(), updateProductDto.getImage()));
    }

    @DisplayName("deleteProduct 성공 테스트")
    @Test
    void deleteProduct() {
        int id = jdbcProductDao.insertProduct(productEntity);
        jdbcProductDao.deleteProduct(id);

        List<ProductEntity> productEntities = jdbcProductDao.selectAllProducts();

        assertThat(productEntities.size()).isZero();
    }

}