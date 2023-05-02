package cart.dao;

import cart.entity.Product;
import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void beforeEach() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("데이터 베이스에 저장되어 있는 상품 모두를 조회한다.")
    void selectAll() {
        List<Product> products = productDao.selectAll();

        assertThat(products).hasSize(3);
    }

    @Test
    @DisplayName("새로운 상품을 저장하여, 기존에 존재하는 상품 2개에 1개가 추가된 3개가 맞는지 확인한다.")
    void save() {
        productDao.save(
                new Product.Builder()
                        .name(Name.of("샐러드"))
                        .price(Price.of(10000))
                        .imageUrl(Url.of("밋엉씨"))
                        .build()
        );

        List<Product> products = productDao.selectAll();
        assertThat(products).hasSize(3);
    }

    @Test
    @DisplayName("기존의 상품을 삭제하여, 기존의 상품 2개에 1개가 삭제된 1개가 맞는지 확인한다.")
    void deleteById() {
        productDao.deleteById(1);

        assertThat(productDao.selectAll()).hasSize(1);
    }

    @Test
    @DisplayName("기존의 상품을 수정하고, 데이터가 수정되었는지 확인한다.")
    void updateById() {
        productDao.updateById(1, new Product.Builder()
                        .name(Name.of("skdkfkak"))
                        .price(Price.of(10000))
                        .imageUrl(Url.of("asdfkasdfk"))
                        .build()
        );

        boolean isExistEqualProduct = false;
        for (Product product : productDao.selectAll()) { // 현재 Product 에 Equals 와 hashCode 가 정의될 이유는 없는 것 같아서, 이렇게 순수하게 작성하였습니다.. 코드가 더러워도 이해 부탁드립니다..
            if (product.getName().equals("skdkfkak")
                    && product.getPrice() == 10000
                    && product.getImageUrl().equals("asdfkasdfk")) {
                isExistEqualProduct = true;
            }
        }

        assertThat(isExistEqualProduct).isTrue();
    }

}
