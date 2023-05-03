package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import cart.dao.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.Dao;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.entity.Product;

@Service
@Transactional
public class ProductService {
    private final Dao dao;

    public ProductService(ProductDao dao) {
        this.dao = dao;
    }

    public int addProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(productRequestDto.getName(), productRequestDto.getPrice(),
            productRequestDto.getImage());
        return dao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> selectAllProducts() {
        final List<Product> products = dao.selectAll();
        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateProduct(ProductRequestDto productRequestDto, int productId) {
        Product product = new Product(productId, productRequestDto.getName(),
            productRequestDto.getPrice(), productRequestDto.getImage());
        int updatedResult = dao.update(product);
        if (updatedResult == 0) {
            throw new IllegalStateException("존재하지 않는 상품입니다.");
        }
    }

    public void deleteProduct(int productId) {
        int deletedResult = dao.delete(productId);
        if (deletedResult == 0) {
            throw new IllegalStateException("존재하지 않는 상품입니다.");
        }
    }

}
