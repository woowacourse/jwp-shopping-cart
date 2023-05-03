package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.entity.Product;

@Service
@Transactional
public class ProductService {
    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int addProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(productRequestDto.getName(), productRequestDto.getPrice(),
            productRequestDto.getImage());
        return productDao.insertProduct(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> selectAllProducts() {
        return productDao.selectAllProducts()
            .stream()
            .map(ProductDto::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public void updateProduct(ProductRequestDto productRequestDto, int productId) {
        Product product = new Product(productId, productRequestDto.getName(),
            productRequestDto.getPrice(), productRequestDto.getImage());
        int updatedResult = productDao.updateProduct(product);
        if (updatedResult == 0) {
            throw new IllegalStateException("존재하지 않는 상품입니다.");
        }
    }

    public void deleteProduct(int productId) {
        int deletedResult = productDao.deleteProduct(productId);
        if (deletedResult == 0) {
            throw new IllegalStateException("존재하지 않는 상품입니다.");
        }
    }

}
