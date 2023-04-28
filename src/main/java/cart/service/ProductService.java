package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.dto.ProductAddRequest;
import cart.dto.ProductModifyRequest;
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
        int updateRowNumber = productDao.update(new ProductEntity(id, productModifyRequest.getName(), productModifyRequest.getImgUrl(), productModifyRequest.getPrice()));
        if (updateRowNumber == 0) {
            throw new IllegalArgumentException("해당하는 ID가 없습니다.");
        }
    }

    public void delete(int id) {
        int deleteRowNumber = productDao.delete(id);
        if (deleteRowNumber == 0) {
            throw new IllegalArgumentException("해당하는 ID가 없습니다.");
        }
    }
}
