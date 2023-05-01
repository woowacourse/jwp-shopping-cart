package cart.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.product.dao.FakeProductDao;
import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import cart.product.dto.RequestProductDto;
import cart.product.dto.ResponseProductDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductListServiceTest {

    @Test
    @DisplayName("display 테스트")
    void display() {
        // given
        final FakeProductDao fakeProductDao = new FakeProductDao();
        fakeProductDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        fakeProductDao.insert(new Product(new Name("에코"), "http://echo", new Price(2000)));
        final ProductListService productListService = new ProductListService(fakeProductDao);
        // when
        final List<ResponseProductDto> responseDtos = productListService.display();
        // then
        assertEquals(responseDtos.get(0).getName(), "망고");
        assertEquals(responseDtos.get(0).getImage(), "http://mango");
        assertEquals(responseDtos.get(0).getPrice(), 1000);
        assertEquals(responseDtos.get(1).getName(), "에코");
        assertEquals(responseDtos.get(1).getImage(), "http://echo");
        assertEquals(responseDtos.get(1).getPrice(), 2000);
    }

    @Test
    @DisplayName("create 테스트")
    void create() {
        // given
        final FakeProductDao fakeProductDao = new FakeProductDao();
        final ProductListService productListService = new ProductListService(fakeProductDao);
        // when
        final RequestProductDto requestProductDto = new RequestProductDto("망고", "http://mango", 1000);
        productListService.create(requestProductDto);
        // then
        final Product product = fakeProductDao.findByID(0).orElseThrow();
        assertEquals(product.getName().getValue(), "망고");
        assertEquals(product.getImage(), "http://mango");
        assertEquals(product.getPrice().getValue(), 1000);
    }

    @Test
    @DisplayName("update 테스트")
    void update() {
        // given
        final FakeProductDao fakeProductDao = new FakeProductDao();
        fakeProductDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        final ProductListService productListService = new ProductListService(fakeProductDao);
        // when
        final RequestProductDto requestProductDto = new RequestProductDto("에코", "http://echo", 1000);
        final ResponseProductDto responseProductDto = productListService.update(0, requestProductDto);
        // then
        assertEquals(responseProductDto.getName(), "에코");
        assertEquals(responseProductDto.getImage(), "http://echo");
        assertEquals(responseProductDto.getPrice(), 1000);
    }

    @Test
    @DisplayName("delete 테스트")
    void delete() {
        // given
        final FakeProductDao fakeProductDao = new FakeProductDao();
        fakeProductDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        final ProductListService productListService = new ProductListService(fakeProductDao);
        // when
        productListService.delete(0);
        // then
        assertEquals(fakeProductDao.findAll().size(), 0);
    }
}
