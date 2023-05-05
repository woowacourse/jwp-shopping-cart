package cart.service;

import cart.controller.exception.CartHasDuplicatedItemsException;
import cart.domain.cart.Cart;
import cart.domain.cart.Item;
import cart.domain.product.Product;
import cart.dto.response.CartResponse;
import cart.dto.response.ItemResponse;
import cart.dto.response.ProductResponse;
import cart.persistence.CartDao;
import cart.persistence.ProductsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final CartDao cartDao;
    private final ProductsDao productsDao;

    public CartService(CartDao cartDao, ProductsDao productsDao) {
        this.cartDao = cartDao;
        this.productsDao = productsDao;
    }

    public ItemResponse createItem(Long memberId, Long productId) {
        Cart memberCart = getCartByMemberId(memberId);
        Item requestedItem = new Item(memberId, productId);

        if (memberCart.contains(requestedItem)) {
            throw new CartHasDuplicatedItemsException();
        }

        Long registeredItemId = cartDao.createItem(requestedItem.getMemberId(), requestedItem.getProductId());
        Item registeredItem = cartDao.findItemById(registeredItemId);

        ProductResponse productResponse = convertItemToProductResponse(registeredItem);

        return new ItemResponse(
                registeredItem.getId(),
                registeredItem.getMemberId(),
                productResponse
        );
    }

    private Cart getCartByMemberId(Long memberId) {
        List<Item> items = cartDao.findAllItemsByMemberId(memberId);
        return new Cart(memberId, items);
    }

    public CartResponse readAllItemsByMemberId(Long memberId) {
        List<Item> items = cartDao.findAllItemsByMemberId(memberId);

        List<ItemResponse> responses = items.stream()
                .map(item -> new ItemResponse(
                        item.getId(),
                        item.getMemberId(),
                        convertItemToProductResponse(item))
                ).collect(Collectors.toList());

        return new CartResponse(memberId, responses);
    }

    private ProductResponse convertItemToProductResponse(Item item) {
        Product product = productsDao.findById(item.getProductId());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    public void deleteItemById(Long id) {
        cartDao.deleteItemById(id);
    }
}
