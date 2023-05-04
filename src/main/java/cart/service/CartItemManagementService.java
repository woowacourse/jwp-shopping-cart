package cart.service;

import cart.dao.CartItemDao;
import cart.dao.ProductDao;
import cart.dto.CartItemCreationDto;
import cart.dto.CartItemDetailsDto;
import cart.domain.entity.CartItemEntity;
import cart.domain.entity.ProductEntity;
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

    public List<CartItemDetailsDto> findAllByMemberId(final Long memberId) {
        List<CartItemEntity> cartItemEntities = cartItemDao.selectAllByMemberId(memberId);
        List<CartItemDetailsDto> cartItemDetailsDtos = new ArrayList<>();
        for (CartItemEntity cartItemEntity : cartItemEntities) {
            ProductEntity productEntity = productDao.selectById(cartItemEntity.getProductId());
            cartItemDetailsDtos.add(CartItemDetailsDto.from(cartItemEntity.getId(), productEntity));
        }
        return cartItemDetailsDtos;
    }

    public long save(final CartItemCreationDto cartItemCreationDto) {
        return cartItemDao.insert(CartItemCreationDto.toEntity(cartItemCreationDto));
    }

    public void deleteById(final Long id){
        int deletedRowCount = cartItemDao.deleteById(id);
        if (deletedRowCount == 0) {
            throw new IllegalArgumentException("장바구니에 없는 상품입니다.");
        }
    }

}
