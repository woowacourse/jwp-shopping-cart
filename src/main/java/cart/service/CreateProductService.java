package cart.service;

import cart.dao.ProductDao;
import cart.entiy.ProductEntity;

public class CreateProductService {

    private final ProductDao productDao;

    public CreateProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductEntity create(final String name, final String image, final int price) {
        return productDao.save(new ProductEntity(name, image, price));
    }
}
