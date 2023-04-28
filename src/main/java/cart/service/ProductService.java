package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.dto.ProductAddRequest;
import cart.dto.ProductModifyRequest;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int save(ProductAddRequest productAddRequest) {
        return productDao.save(productAddRequest);
    }

    public List<ProductDto> findAll() {
        return productDao.findAll();
    }

    public void update(ProductModifyRequest productModifyRequest, int id) {
        int updateRowNumber = productDao.update(productModifyRequest, id);
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
