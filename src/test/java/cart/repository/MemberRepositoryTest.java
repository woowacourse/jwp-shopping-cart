package cart.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.member.Member;
import cart.domain.member.MemberId;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;

	@DisplayName("회원 저장 테스트")
	@Test
	void insert() {
		// given
		final Member member = new Member("kiara", "email@email", "pw");

		// when
		MemberId insertId = memberRepository.insert(member);
		final Member foundMember = memberRepository.findByMemberId(insertId);

		// then
		Assertions.assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
	}

	@DisplayName("전체 회원 조회 테스트")
	@Test
	void findAll() {
		// given
		final Member member = new Member("kiara", "email@email", "pw");

		// when
		memberRepository.insert(member);
		List<Member> allMembers = memberRepository.findAll();

		// then
		Assertions.assertThat(allMembers).hasSize(3);
	}

	@DisplayName("ID로 회원 조회 테스트")
	@Test
	void findByMemberId() {
		// given거
		MemberId memberId = memberRepository.insert(new Member("kiara", "email@email", "pw"));

		// when
		Member memberById = memberRepository.findByMemberId(memberId);

		// then
		Assertions.assertThat(memberById)
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(new Member("kiara", "email@email", "pw"));
	}

	@DisplayName("email로 회원 조회 테스트")
	@Test
	void findByEmail() {
		// given
		memberRepository.insert(new Member("kiara", "email@email", "pw"));

		// when
		final Member memberByEmail = memberRepository.findByEmail("email@email");

		// then
		Assertions.assertThat(memberByEmail)
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(new Member("kiara", "email@email", "pw"));
	}
}
