package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.MemberRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.entity.CartEntity;
import cart.dto.entity.MemberEntity;
import cart.exception.NotFoundUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartDao cartDao;

    private final MemberDao memberDao;

    public CartService(CartDao cartDao, MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    public List<ProductResponseDto> findAll(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        Optional<MemberEntity> findMember = memberDao.findByEmail(new MemberEntity(member.getEmail(), member.getPassword()));
        findMember.orElseThrow(NotFoundUserException::new);

        return cartDao.findCartByMember(findMember.get().getId())
                .stream()
                .map(entity -> new ProductResponseDto(entity.getProductId(),
                        entity.getProductName(),
                        entity.getProductImage(),
                        entity.getProductPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void save(MemberRequestDto memberRequestDto, Long id) {
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        Optional<MemberEntity> findMember = memberDao.findByEmail(new MemberEntity(member.getEmail(), member.getPassword()));
        findMember.orElseThrow(NotFoundUserException::new);

        cartDao.save(new CartEntity(id, findMember.get().getId()));
    }

    @Transactional
    public void delete(MemberRequestDto memberRequestDto, Long id) {
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        Optional<MemberEntity> findMember = memberDao.findByEmail(new MemberEntity(member.getEmail(), member.getPassword()));
        findMember.orElseThrow(NotFoundUserException::new);

        cartDao.delete(new CartEntity(id, findMember.get().getId()));
    }
}
