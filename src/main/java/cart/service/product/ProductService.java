package cart.service.product;

import cart.service.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long create(String productName, String productImageUrl, int productPrice) {
//        Product product = new Product(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        Product product = new Product(
                new ProductName(productName),
                new ProductImage(productImageUrl),
                new ProductPrice(productPrice)
        );
        return productDao.save(product);
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getImageUrl(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductResponse update(String productName, String productImageUrl, int productPrice, Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        Product newInfoProduct = product.replaceProduct(
                new ProductName(productName),
                new ProductImage(productImageUrl),
                new ProductPrice(productPrice),
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
