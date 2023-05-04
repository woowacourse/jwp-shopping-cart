package cart.repository.dao.productDao;

import cart.entity.Product;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JdbcProductDaoTest {

    @Autowired
    private DataSource dataSource;

    private JdbcProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new JdbcProductDao(dataSource);
    }

    @Test
    void 상품을_저장한다() {
        String name = "chocoball";
        String imageUrl = "https://www.naver.png";
        int price = 4000;
        Product product = new Product(name, imageUrl, price);

        Long id = productDao.save(product);

        assertThat(id).isPositive();
    }

    @Test
    void 상품ID로_상품을_찾는다() {
        String name = "chocoball";
        String imageUrl = "https://www.naver.png";
        int price = 4000;
        Product product = new Product(name, imageUrl, price);
        Long id = productDao.save(product);

        Optional<Product> findProduct = productDao.findById(id);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(findProduct.isPresent()).isTrue();
        softAssertions.assertThat(findProduct.get().getName()).isEqualTo(name);
        softAssertions.assertThat(findProduct.get().getImageUrl()).isEqualTo(imageUrl);
        softAssertions.assertThat(findProduct.get().getPrice()).isEqualTo(price);
        softAssertions.assertAll();
    }

    @Test
    void 없는상품ID로_상품을_찾으면_빈_옵셔널을_반환한다() {
        String name = "chocoball";
        String imageUrl = "https://www.naver.png";
        int price = 4000;
        Product product = new Product(name, imageUrl, price);
        Long id = productDao.save(product);

        assertThat(productDao.findById(id + 1).isEmpty()).isTrue();
    }

    @Test
    void 전체_상품을_찾는다() {
        String nameFirst = "chocoball";
        String imageUrlFirst = "https://www.naver.png";
        int priceFirst = 4000;
        Product firstProduct = new Product(nameFirst, imageUrlFirst, priceFirst);
        String nameSecond = "snack";
        String imageUrlSecond = "https://www.snack.png";
        int priceSecond = 4000;
        Product secondProduct = new Product(nameSecond, imageUrlSecond, priceSecond);
        productDao.save(firstProduct);
        productDao.save(secondProduct);

        List<Product> products = productDao.findAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void 상품을_수정한다() {
        String name = "chocoball";
        String imageUrl = "https://www.naver.png";
        int price = 4000;
        Product product = new Product(name, imageUrl, price);
        Long id = productDao.save(product);

        Long updateId = id;
        String updateName = "snack";
        String updateImageUrl = "https://www.snack.png";
        int updatePrice = 5000;
        Product updateProduct = new Product(updateId, updateName, updateImageUrl, updatePrice);

        productDao.update(updateProduct);
        Optional<Product> updatedProduct = productDao.findById(id);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(updatedProduct.isPresent()).isTrue();
        softAssertions.assertThat(updatedProduct.get().getName()).isEqualTo(updateName);
        softAssertions.assertThat(updatedProduct.get().getImageUrl()).isEqualTo(updateImageUrl);
        softAssertions.assertThat(updatedProduct.get().getPrice()).isEqualTo(updatePrice);
        softAssertions.assertAll();
    }

    @Test
    void 상품을_삭제한다() {
        String name = "chocoball";
        String imageUrl = "https://www.naver.png";
        int price = 4000;
        Product product = new Product(name, imageUrl, price);
        Long id = productDao.save(product);

        int amountOfProductDeleted = productDao.delete(id);

        assertThat(amountOfProductDeleted).isEqualTo(1);
    }
}
