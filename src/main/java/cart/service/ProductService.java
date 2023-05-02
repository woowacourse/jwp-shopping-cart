package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
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
    public long saveProduct(ProductSaveRequestDto productSaveRequestDto) {
        Product product = new Product(productSaveRequestDto.getName(), productSaveRequestDto.getImage(),
                productSaveRequestDto.getPrice());
        return productDao.save(product);
    }

    public List<ProductDto> findAllProducts() {
        List<Product> products = productDao.findAllProducts();
        return products.stream()
                .map(product -> new ProductDto(product.getProductId(), product.getName(), product.getImage(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }

    public void updateProduct(ProductUpdateRequestDto productUpdateRequestDto) {
        Product product = new Product(productUpdateRequestDto.getProductId(), productUpdateRequestDto.getName(),
                productUpdateRequestDto.getImage(), productUpdateRequestDto.getPrice());
        productDao.updateProduct(product);
    }

    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }
}
