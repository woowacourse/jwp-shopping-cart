package cart.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.catalog.service.ProductCatalogService;
import cart.product.dao.ProductDao;
import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import cart.product.dto.ResponseProductDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductCatalogServiceTest {
    
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
        public Product findByName(final String name) {
            return null;
        }
        
        @Override
        public void deleteByID(final long id) {
        
        }
        
        @Override
        public void update(final Product product) {
        
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
        final ProductCatalogService productCatalogService = new ProductCatalogService(fakeProductDao);
        
        final List<ResponseProductDto> responseDtos = productCatalogService.display();
        
        assertEquals(responseDtos.get(0).getName(), "망고");
        assertEquals(responseDtos.get(0).getImage(), "http://mango");
        assertEquals(responseDtos.get(0).getPrice(), 1000);
        assertEquals(responseDtos.get(1).getName(), "에코");
        assertEquals(responseDtos.get(1).getImage(), "http://echo");
        assertEquals(responseDtos.get(1).getPrice(), 2000);
    }
}