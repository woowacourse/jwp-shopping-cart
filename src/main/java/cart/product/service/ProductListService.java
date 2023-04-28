package cart.product.service;

import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import cart.product.dto.RequestProductDto;
import cart.product.dto.ResponseProductDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductListService {
    
    private final ProductDao productDao;
    
    public ProductListService(final ProductDao productDao) {
        this.productDao = productDao;
    }
    
    public List<ResponseProductDto> display() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(ResponseProductDto::create)
                .collect(Collectors.toList());
    }
    
    public void create(final RequestProductDto requestProductDto) {
        final Product product = Product.create(requestProductDto);
        productDao.insert(product);
    }
    
    public ResponseProductDto update(final long id, final RequestProductDto requestProductDto) {
        final Product originalProduct = productDao.findByID(id);
        final Product updatedProduct = originalProduct.update(requestProductDto);
        productDao.update(updatedProduct);
        return ResponseProductDto.create(updatedProduct);
    }
    
    public void delete(final long id) {
        productDao.deleteByID(id);
    }
}
