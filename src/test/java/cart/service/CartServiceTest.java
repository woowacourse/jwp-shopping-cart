package cart.service;

import cart.dao.ProductJdbcDao;
import cart.domain.dto.CartDto;
import cart.entity.ProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductJdbcDao productJdbcDao;

    @Test
    void saveProduct() {
        ProductEntity productEntity = new ProductEntity("비버", "a", 10000L);
        Integer productId = productJdbcDao.insert(productEntity);

        cartService.addProduct(Long.valueOf(productId), 1L);

        List<CartDto> cartDtos = cartService.getProducts(1L);

        Assertions.assertThat(cartDtos.get(0).getProductId()).isEqualTo(Long.valueOf(productId));
    }
}