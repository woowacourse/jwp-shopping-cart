package cart.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.product.dao.FakeProductDao;
import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import cart.product.dto.ResponseProductDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductListServiceTest {

    private final FakeProductDao fakeProductDao = new FakeProductDao();
    
    @Test
    @DisplayName("display 테스트")
    void display() {
        // given
        fakeProductDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        fakeProductDao.insert(new Product(new Name("에코"), "http://echo", new Price(2000)));
        // when
        final ProductListService productListService = new ProductListService(fakeProductDao);
        final List<ResponseProductDto> responseDtos = productListService.display();
        // then
        assertEquals(responseDtos.get(0).getName(), "망고");
        assertEquals(responseDtos.get(0).getImage(), "http://mango");
        assertEquals(responseDtos.get(0).getPrice(), 1000);
        assertEquals(responseDtos.get(1).getName(), "에코");
        assertEquals(responseDtos.get(1).getImage(), "http://echo");
        assertEquals(responseDtos.get(1).getPrice(), 2000);
    }
}
