package woowacourse.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Member;
import woowacourse.auth.dto.MemberRequest;
import woowacourse.auth.exception.InvalidMemberException;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

	private final MemberDao memberDao;

	public Member signUp(MemberRequest request) {
		Member member = request.toEntity();
		validateEmailDuplicated(member);
		return memberDao.save(member);
	}

	private void validateEmailDuplicated(Member member) {
		if (memberDao.existByEmail(member.getEmail())) {
			throw new InvalidMemberException("중복된 이메일 입니다.");
		}
	}
}

