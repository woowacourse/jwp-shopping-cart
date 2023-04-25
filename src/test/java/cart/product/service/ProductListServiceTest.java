package cart.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.product.dao.ProductDao;
import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import cart.product.dto.ProductDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductListServiceTest {
    
    static class FakeProductDao implements ProductDao {
        
        @Override
        public long insert(final Product product) {
            return 0;
        }
        
        @Override
        public Product findByID(final long id) {
            return null;
        }
        
        @Override
        public List<Product> findAll() {
            final Product product1 = new Product(new Name("망고"), "http://mango", new Price(1000));
            final Product product2 = new Product(new Name("에코"), "http://echo", new Price(2000));
            return List.of(product1, product2);
        }
    }
    
    @Test
    @DisplayName("display 테스트")
    void display() {
        final FakeProductDao fakeProductDao = new FakeProductDao();
        final ProductListService productListService = new ProductListService(fakeProductDao);
        
        final List<ProductDto> productDtos = productListService.display();
        
        assertEquals(productDtos.get(0).getName(), "망고");
        assertEquals(productDtos.get(0).getImage(), "http://mango");
        assertEquals(productDtos.get(0).getPrice(), 1000);
        assertEquals(productDtos.get(1).getName(), "에코");
        assertEquals(productDtos.get(1).getImage(), "http://echo");
        assertEquals(productDtos.get(1).getPrice(), 2000);
    }
}