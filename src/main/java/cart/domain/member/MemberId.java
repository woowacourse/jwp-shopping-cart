package cart.domain.member;

import java.util.Objects;

public class MemberId {
	private final long id;

	public MemberId(final long id) {
		this.id = id;
	}

	public static MemberId from(final long id) {
		return new MemberId(id);
	}

	public long getId() {
		return id;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final MemberId memberId = (MemberId)o;
		return id == memberId.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
