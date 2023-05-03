package cart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.dao.product.FakeProductDao;
import cart.domain.product.Name;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.dto.product.RequestProductDto;
import cart.dto.product.ResponseProductDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    @Test
    @DisplayName("display 테스트")
    void display() {
        // given
        final FakeProductDao fakeProductDao = new FakeProductDao();
        fakeProductDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        fakeProductDao.insert(new Product(new Name("에코"), "http://echo", new Price(2000)));
        final ProductService productService = new ProductService(fakeProductDao);
        // when
        final List<ResponseProductDto> responseDtos = productService.display();
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
        final ProductService productService = new ProductService(fakeProductDao);
        // when
        final RequestProductDto requestProductDto = new RequestProductDto("망고", "http://mango", 1000);
        productService.create(requestProductDto);
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
        final ProductService productService = new ProductService(fakeProductDao);
        // when
        final RequestProductDto requestProductDto = new RequestProductDto("에코", "http://echo", 1000);
        final ResponseProductDto responseProductDto = productService.update(0, requestProductDto);
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
        final ProductService productService = new ProductService(fakeProductDao);
        // when
        productService.delete(0);
        // then
        assertEquals(fakeProductDao.findAll().size(), 0);
    }
}
