package cart.service;

import cart.controller.ProductRegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void registerProduct(final ProductRegisterRequest productRegisterRequest) {
        ProductEntity productEntity = new ProductEntity(productRegisterRequest.getName(),
                                                        productRegisterRequest.getPrice(),
                                                        productRegisterRequest.getImageUrl());

        productDao.save(productEntity);
    }
}
