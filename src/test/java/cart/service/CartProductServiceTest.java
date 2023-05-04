package cart.service;

import cart.dao.cart.JdbcCartDao;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static cart.DummyData.DUMMY_MEMBER_ONE;
import static cart.DummyData.DUMMY_PRODUCT_ONE;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CartProductServiceTest {

    @InjectMocks
    CartProductService cartProductService;

    @Mock
    MemberService memberService;

    @Mock
    JdbcCartDao cartDao;

    @Test
    void 특정_유저의_장바구니에_상품이_등록되는지_확인한다() {
        when(memberService.find(any(), any())).thenReturn(DUMMY_MEMBER_ONE);
        doNothing().when(cartDao).insert(any(), any());

        cartProductService.addCartProduct(DUMMY_PRODUCT_ONE.getId(), DUMMY_MEMBER_ONE.getEmail(), DUMMY_MEMBER_ONE.getPassword());

        SoftAssertions.assertSoftly(softAssertions -> {
            verify(memberService, times(1)).find(any(), any());
            verify(cartDao, times(1)).insert(any(), any());
        });
    }

    @Test
    void 특정_유저가_등록한_장바구니_상품_목록을_가져오는지_확인한다() {
        when(memberService.find(any(), any())).thenReturn(DUMMY_MEMBER_ONE);
        when(cartDao.findAllProductIdByMemberId(any())).thenReturn(List.of());

        cartProductService.findAllProductIds(DUMMY_MEMBER_ONE.getEmail(), DUMMY_MEMBER_ONE.getPassword());

        SoftAssertions.assertSoftly(softAssertions -> {
            verify(memberService, times(1)).find(any(), any());
            verify(cartDao, times(1)).findAllProductIdByMemberId(any());
        });
    }

    @Test
    void 특정_유저의_장바구니_상품을_제거하는지_확인한다() {
        when(memberService.find(any(), any())).thenReturn(DUMMY_MEMBER_ONE);
        doNothing().when(cartDao).deleteByMemberIdAndProductId(any(), any());

        cartProductService.delete(DUMMY_PRODUCT_ONE.getId(), DUMMY_MEMBER_ONE.getEmail(), DUMMY_MEMBER_ONE.getPassword());

        SoftAssertions.assertSoftly(softAssertions -> {
            verify(memberService, times(1)).find(any(), any());
            verify(cartDao, times(1)).deleteByMemberIdAndProductId(any(), any());
        });
    }
}

