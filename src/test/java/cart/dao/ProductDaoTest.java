package cart.dao;

import static cart.fixture.ProductFixture.PRODUCT_A;
import static cart.fixture.ProductFixture.PRODUCT_B;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import java.util.List;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductDaoTest {

    @Autowired
    private DataSource dataSource;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(dataSource);
    }

    @Test
    @DisplayName("상품을 정상적으로 저장한다.")
    void save_product() {
        //when
        Long id = productDao.save(PRODUCT_A);
        //then
        assertThat(id).isPositive();
    }

    @Test
    @DisplayName("상품을 아이디를 통해 반환한다.")
    void find_by_id_success() {
        //given
        Long id = productDao.save(PRODUCT_A);
        //when
        Product savedProduct = productDao.findById(id);
        //then
        assertAll(
                () -> assertThat(savedProduct).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(PRODUCT_A),
                () -> assertThat(savedProduct.getId()).isPositive());
    }

    @Test
    @DisplayName("존재하지 않는 id의 상품을 조회시 예외를 반환한다.")
    void find_by_id_fail_by_no_id() {
        //when && then
        assertThatThrownBy(() -> productDao.findById(10000L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @Test
    @DisplayName("저장된 모든 상품 데이터를 가져온다")
    void findAll() {
        //given
        productDao.save(PRODUCT_A);
        productDao.save(PRODUCT_B);
        //when
        List<Product> actual = productDao.findAll();
        //then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품데이터를 삭제한다.")
    void delete_success() {
        //given
        Long id = productDao.save(PRODUCT_A);
        //when
        productDao.deleteById(id);
        //then
        assertThatThrownBy(() -> productDao.findById(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @Test
    @DisplayName("상품 데이터를 업데이트한다.")
    void update_success() {
        //given
        Long id = productDao.save(PRODUCT_A);
        //when
        productDao.updateById(id, PRODUCT_B);
        //then
        Product actual = productDao.findById(id);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(PRODUCT_B);
    }
}
