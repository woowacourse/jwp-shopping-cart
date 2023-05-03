package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.entity.ProductEntity;
import cart.exception.ResourceNotFoundException;
import cart.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;
    private final ProductMapper productMapper;

    public ProductService(ProductDao productDao, ProductMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    public ProductResponse create(ProductRequest productRequest) {
        Product product = productMapper.requestToProduct(productRequest);
        ProductEntity created = productDao.save(product)
                .orElseThrow(() -> new ResourceNotFoundException("데이터가 정상적으로 저장되지 않았습니다."));
        return productMapper.entityToResponse(created);
    }

    public List<ProductResponse> findAll() {
        List<ProductEntity> products = productDao.findAll();
        return products.stream()
                .map(productMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse update(ProductRequest productRequest, Long id) {
        ProductEntity productEntity = productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        productEntity.replace(productRequest);
        productDao.update(productEntity);
        return productMapper.entityToResponse(productEntity);
    }

    public void deleteById(Long id) {
        productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        productDao.deleteById(id);
    }
}
