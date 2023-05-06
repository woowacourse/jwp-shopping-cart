package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductAddRequest;
import cart.dto.ProductDto;
import cart.dto.ProductModifyRequest;
import cart.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int save(ProductAddRequest productAddRequest) {
        return productDao.save(new ProductEntity(productAddRequest.getName(), productAddRequest.getImgUrl(), productAddRequest.getPrice()));
    }

    public List<ProductDto> findAll() {
        return productDao.findAll()
                .stream()
                .map(it -> new ProductDto(it.getId(), it.getName(), it.getImgUrl(), it.getPrice()))
                .collect(Collectors.toList());
    }

    public void update(ProductModifyRequest productModifyRequest, int id) {
        productDao.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 ID가 없습니다."));
        productDao.update(new ProductEntity(
                id,
                productModifyRequest.getName(),
                productModifyRequest.getImgUrl(),
                productModifyRequest.getPrice()));
    }

    public void delete(int id) {
        productDao.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 ID가 없습니다."));
        productDao.delete(id);
    }
}
