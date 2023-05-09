package cart.service.product;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.dto.application.ProductDto;
import cart.dto.application.ProductEntityDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductEnrollService {

    private final ProductDao productDao;

    public ProductEnrollService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public ProductEntityDto register(final ProductDto productDto) {
        final Product newProduct = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());

        final long id = productDao.insert(newProduct);

        return new ProductEntityDto(id, productDto);
    }
}
