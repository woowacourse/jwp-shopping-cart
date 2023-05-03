package cart.catalog.service;

import cart.catalog.dao.CatalogDao;
import cart.product.domain.Product;
import cart.product.dto.RequestProductDto;
import cart.product.dto.ResponseProductDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogService {
    
    private final CatalogDao catalogDao;
    
    public ProductCatalogService(final CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }
    
    public List<ResponseProductDto> display() {
        final List<Product> products = this.catalogDao.findAll();
        return products.stream()
                .map(ResponseProductDto::create)
                .collect(Collectors.toList());
    }
    
    public void create(final RequestProductDto requestProductDto) {
        final Product product = Product.create(requestProductDto);
        this.catalogDao.insert(product);
    }
    
    public ResponseProductDto update(final long id, final RequestProductDto requestProductDto) {
        final Product originalProduct = this.catalogDao.findByID(id);
        final Product updatedProduct = originalProduct.update(requestProductDto);
        this.catalogDao.update(updatedProduct);
        return ResponseProductDto.create(updatedProduct);
    }
    
    public void delete(final long id) {
        this.catalogDao.deleteByID(id);
    }
}
