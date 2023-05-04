package cart.service;

import cart.dao.ProductDao;
import cart.dao.UserDao;
import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateRequestDto;
import cart.dto.UserResponseDto;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final int MINAFFECTEDROW = 1;

    private final ProductDao productDao;
    private final UserDao userDao;

    public CartService(ProductDao productDao, UserDao userDao) {
        this.productDao = productDao;
        this.userDao = userDao;
    }

    public void addProduct(final InsertRequestDto insertRequestDto) {
        productDao.insert(insertRequestDto.toEntity());
    }

    public List<ProductResponseDto> getProducts() {
        final List<ProductEntity> products = productDao.selectAll();

        return products.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getImage(),
                        product.getName(),
                        product.getPrice()
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateProduct(final UpdateRequestDto updateRequestDto) {
        int affectedRow = productDao.update(updateRequestDto.toEntity());
        if (affectedRow < MINAFFECTEDROW) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    public void deleteProduct(final int productId) {
        int affectedRow = productDao.delete(productId);
        if (affectedRow < MINAFFECTEDROW) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    public List<UserResponseDto> getUsers() {
        final List<UserEntity> users = userDao.selectAll();

        return users.stream()
                .map(user -> new UserResponseDto(
                        user.getEmail(),
                        user.getPassword()
                ))
                .collect(Collectors.toUnmodifiableList());
    }
}
