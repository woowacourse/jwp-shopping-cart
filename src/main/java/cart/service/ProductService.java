package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    //todo (질문2) : 현재 product 도메인 객체는 id값이 null인 불완전한 객체입니다.
    // 방법 1. 이대로, null인 도메인 객체를 사용한다
    // 방법 2. 도메인 객체에서 id 필드를 제거한다.
    public ProductDto saveProduct(ProductRequestDto request) {
        Product product = new Product(request.getName(), request.getImage(), request.getPrice());
        long productId = productDao.save(product);
        return new ProductDto(productId, product.getName(), request.getImage(), product.getPrice());
    }

    public List<ProductDto> findAllProducts() {
        List<Product> products = productDao.findAllProducts();
        return products.stream()
                .map(product -> new ProductDto(product.getProductId(), product.getName(), product.getImage(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductDto updateProduct(long productId, ProductRequestDto request) {
        validatedProductId(productId);
        Product product = new Product(productId, request.getName(), request.getImage(), request.getPrice());
        productDao.updateProduct(product);
        return new ProductDto(productId, product.getName(), product.getImage(), product.getPrice());
    }

    public void deleteProduct(long productId) {
        validatedProductId(productId);
        productDao.deleteProduct(productId);
    }

    private void validatedProductId(long productId) {
        if (productDao.findProductById(productId).isEmpty()) {
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다");
        }
    }
}
