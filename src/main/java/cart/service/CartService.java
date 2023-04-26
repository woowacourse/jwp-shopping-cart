package cart.service;

import cart.dto.RequestCreateProductDto;
import cart.dto.RequestUpdateProductDto;
import cart.dto.ResponseProductDto;
import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductDao productDao;

    @Autowired
    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ResponseProductDto> findAll() {
        final List<ProductEntity> productEntities = productDao.selectAll();
        return productEntities.stream()
                .map(entity -> new ResponseProductDto(entity.getId(), entity.getName(), entity.getPrice(), entity.getImage()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void insert(final RequestCreateProductDto requestCreateProductDto) {
        final Product newProduct = new Product(requestCreateProductDto.getName(), requestCreateProductDto.getPrice(), requestCreateProductDto.getImage());
        productDao.insert(newProduct);
    }

    public void update(final RequestUpdateProductDto requestUpdateProductDto) {
        final Product product = new Product(requestUpdateProductDto.getName(), requestUpdateProductDto.getPrice(), requestUpdateProductDto.getImage());
        productDao.update(product, requestUpdateProductDto.getId());
    }

    public void delete(final Long id) {
        productDao.delete(id);
    }
}
