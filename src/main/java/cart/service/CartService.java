package cart.service;

import cart.dao.CartDao;
import cart.domain.cart.Item;
import cart.domain.cart.ItemEntity;
import cart.dto.application.ItemEntityDto;
import cart.dto.application.MemberDto;
import cart.service.product.ProductFindService;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberService memberService;
    private final ProductFindService productFindService;

    public CartService(final CartDao cartDao, final MemberService memberService,
                       final ProductFindService productFindService) {
        this.cartDao = cartDao;
        this.memberService = memberService;
        this.productFindService = productFindService;
    }

    @Transactional(readOnly = true)
    public List<ItemEntityDto> findAll(final MemberDto member) {
        final long memberId = memberService.findMemberId(member);

        final List<ItemEntity> items = cartDao.findAll(memberId);
        final Function<ItemEntity, ItemEntityDto> ItemEntityToItemEntityDto = item -> new ItemEntityDto(
                item.getId(),
                productFindService.find(item.getProductId())
        );

        return items.stream()
                .map(ItemEntityToItemEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void insert(final MemberDto member, final long productId) {
        final long memberId = memberService.findMemberId(member);
        final Item item = new Item(memberId, productId);

        cartDao.insert(item);
    }

    @Transactional
    public void delete(final long itemId, final MemberDto member) {
        final long memberId = memberService.findMemberId(member);
        cartDao.delete(itemId, memberId);
    }
}
