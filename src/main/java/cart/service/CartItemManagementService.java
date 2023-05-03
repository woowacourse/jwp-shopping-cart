package cart.service;

import cart.dao.CartItemDao;
import cart.dao.ProductDao;
import cart.dto.CartAdditionDto;
import cart.dto.CartItemDto;
import cart.entity.CartItemEntity;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemManagementService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemManagementService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItemDto> findAllByMemberId(final Long memberId) {
        List<CartItemEntity> cartItemEntities = cartItemDao.selectAllByMemberId(memberId);
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItemEntity cartItemEntity : cartItemEntities) {
            ProductEntity productEntity = productDao.selectById(cartItemEntity.getProductId());
            cartItemDtos.add(CartItemDto.from(cartItemEntity.getId(), productEntity));
        }
        return cartItemDtos;
    }

    public long save(final CartAdditionDto cartAdditionDto) {
        return cartItemDao.insert(CartAdditionDto.toEntity(cartAdditionDto));
    }

}
