package shoppingbasket.product.service;

import java.util.regex.Pattern;
import shoppingbasket.product.dao.ProductDao;
import shoppingbasket.product.dto.ProductInsertRequestDto;
import shoppingbasket.product.dto.ProductUpdateRequestDto;
import shoppingbasket.product.dto.ProductVerifier;
import shoppingbasket.product.entity.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Pattern isUrl = Pattern.compile(ProductVerifier.IMAGE_URL_REGEX);

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductEntity addProduct(final ProductInsertRequestDto productInsertRequestDto) {
        validateImageUrl(productInsertRequestDto.getImage());
        final ProductEntity product = ProductMapper.toEntity(productInsertRequestDto);
        return productDao.insert(product);
    }

    private void validateImageUrl(final String image) {
        if (!isUrl.matcher(image).matches()) {
            throw new IllegalArgumentException("이미지 주소는 URL 형태로 입력되어야 합니다.");
        }
    }

    public List<ProductEntity> getProducts() {
        return productDao.selectAll();
    }

    public ProductEntity updateProduct(final ProductUpdateRequestDto productUpdateRequestDto) {
        validateImageUrl(productUpdateRequestDto.getImage());
        final ProductEntity product = ProductMapper.toEntity(productUpdateRequestDto);
        return productDao.update(product);
    }

    public int deleteProduct(final int productId) {
       return productDao.delete(productId);
    }

    public ProductEntity findProductById(final int id) {
        return productDao.findById(id);
    }
}
