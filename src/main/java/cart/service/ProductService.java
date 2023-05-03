package cart.service;

import cart.dao.product.ProductDao;
import cart.domain.product.Name;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.dto.product.RequestProductDto;
import cart.dto.product.ResponseProductDto;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final String NO_SUCH_PRODUCT_ERROR = "해당하는 상품이 없습니다.";

    private final ProductDao productDao;
    
    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }
    
    public List<ResponseProductDto> display() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(ResponseProductDto::create)
                .collect(Collectors.toList());
    }
    
    public void create(final RequestProductDto requestProductDto) {
        final Product product = new Product(new Name(requestProductDto.getName()), requestProductDto.getImage(),
                new Price(requestProductDto.getPrice()));
        productDao.insert(product);
    }
    
    public ResponseProductDto update(final long id, final RequestProductDto requestProductDto) {
        productDao.findByID(id).orElseThrow(() -> new NoSuchElementException(NO_SUCH_PRODUCT_ERROR));
        final Product product = new Product(id, new Name(requestProductDto.getName()), requestProductDto.getImage(),
                new Price(requestProductDto.getPrice()));
        productDao.update(product);
        return ResponseProductDto.create(product);
    }
    
    public void delete(final long id) {
        productDao.findByID(id).orElseThrow(() -> new NoSuchElementException(NO_SUCH_PRODUCT_ERROR));
        productDao.deleteByID(id);
    }
}
