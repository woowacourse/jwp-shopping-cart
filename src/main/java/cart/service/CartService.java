package cart.service;

import cart.controller.exception.CartHasDuplicatedItemsException;
import cart.domain.cart.Cart;
import cart.domain.cart.Item;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.mapper.ResponseMapper;
import cart.dto.response.CartResponse;
import cart.dto.response.ItemResponse;
import cart.persistence.CartDao;
import cart.persistence.MembersDao;
import cart.persistence.ProductsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartDao cartDao;
    private final ProductsDao productsDao;
    private final MembersDao membersDao;

    public CartService(CartDao cartDao, ProductsDao productsDao, MembersDao membersDao) {
        this.cartDao = cartDao;
        this.productsDao = productsDao;
        this.membersDao = membersDao;
    }

    public ItemResponse createItem(Long memberId, Long productId) {
        Member member = membersDao.findById(memberId);
        Product product = productsDao.findById(productId);
        Item requestedItem = new Item(member, product);

        Cart memberCart = getCartByMember(member);

        if (memberCart.contains(requestedItem)) {
            throw new CartHasDuplicatedItemsException();
        }

        long registeredItemId = cartDao.saveItem(requestedItem);

        return new ItemResponse(
                registeredItemId,
                requestedItem.getMemberId(),
                ResponseMapper.toProductResponse(requestedItem.getProduct())
        );
    }

    private Cart getCartByMember(Member member) {
        List<Item> items = cartDao.findAllItemsByMemberId(member.getId());
        return new Cart(member, items);
    }

    public CartResponse readAllItemsByMemberId(Long memberId) {
        Member member = membersDao.findById(memberId);
        Cart cart = getCartByMember(member);

        List<ItemResponse> responses = ResponseMapper.toItemResponses(cart.getItems());

        return new CartResponse(member.getId(), responses);
    }

    public void deleteItemById(long id) {
        cartDao.deleteItemById(id);
    }
}
