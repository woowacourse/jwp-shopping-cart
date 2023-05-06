package cart.service.product;

import cart.service.product.domain.Product;
import cart.service.product.domain.ProductImage;
import cart.service.product.domain.ProductName;
import cart.service.product.domain.ProductPrice;
import cart.service.product.dto.ProductResponse;
import cart.service.product.dto.ProductServiceRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long create(ProductServiceRequest productServiceRequest) {
        Product product = new Product(
                new ProductName(productServiceRequest.getName()),
                new ProductImage(productServiceRequest.getImageUrl()),
                new ProductPrice(productServiceRequest.getPrice())
        );
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getImageUrl(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductResponse update(ProductServiceRequest productRequest, Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        Product newInfoProduct = product.replaceProduct(
                new ProductName(productRequest.getName()),
                new ProductImage(productRequest.getImageUrl()),
                new ProductPrice(productRequest.getPrice()),
                id
        );
        Product updateProduct = productDao.update(newInfoProduct);
        return ProductResponse.from(updateProduct);
    }

    public void deleteById(Long id) {
        productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        productDao.deleteById(id);
    }
}
