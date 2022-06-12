package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Pagination;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;

@Service
@Transactional
public class ProductService {
    public static final String NOT_EXIST_PRODUCT = "[ERROR] 없는 상품입니다.";
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductsResponse findProducts(int page, int perPage) {
        Long totalItem = productDao.countTotal();
        Pagination pagination = new Pagination(perPage, page);
        if (pagination.isOverMaxPage(totalItem, page)) {
            return new ProductsResponse(new Products(List.of()).getProducts());
        }
        Products products = new Products(productDao.findProductsByPage(page, perPage));
        return new ProductsResponse(products.getProducts());
    }

    public ProductResponse findProductById(final Long productId) {
        if (!productDao.isValidId(productId)) {
            throw new InvalidProductException(NOT_EXIST_PRODUCT);
        }
        Product product = productDao.findProductById(productId);
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public ProductsResponse findProducts() {
        Products products = new Products(productDao.findProducts());
        return new ProductsResponse(products.getProducts());
    }
}
