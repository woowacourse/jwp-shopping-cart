package cart.service;

import cart.dao.ProductDao;
import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.exception.CustomException;
import cart.exception.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public static final int UPDATE_QUERY_NUMBER = 0;

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int save(ProductRequest productRequest) {
        return productDao.save(productRequest);
    }

    public List<ProductResponse> findAll() {
        return productDao.findAll();
    }

    public void update(ProductRequest productRequest, int id) {
        int updateRowNumber = productDao.update(productRequest, id);
        if (updateRowNumber == UPDATE_QUERY_NUMBER) {
            throw new CustomException(ErrorCode.ID_NOT_FOUND);
        }
    }

    public void delete(int id) {
        int deleteRowNumber = productDao.delete(id);
        if (deleteRowNumber == UPDATE_QUERY_NUMBER) {
            throw new CustomException(ErrorCode.ID_NOT_FOUND);
        }
    }
}
