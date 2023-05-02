package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.dto.application.ProductDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
    public Product register(final ProductDto productDto) {
        final Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        final long id = productDao.insert(product);

        return new Product(id, productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
    }

    @Transactional
    public Product updateProduct(final long id, final ProductDto productDto) {
        validateExistData(id);

        final Product newProduct = new Product(
                id,
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImageUrl()
        );

        productDao.update(newProduct);

        return new Product(id, productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
    }

    @Transactional
    public void deleteProduct(final long id) {
        validateExistData(id);
        productDao.delete(id);
    }

    private void validateExistData(final long id) {
        if (!productDao.isExist(id)) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}