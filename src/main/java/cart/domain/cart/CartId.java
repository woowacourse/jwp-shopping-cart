package cart.domain.cart;

import java.util.Objects;

public class CartId {
	private final long id;

	public CartId(final long id) {
		this.id = id;
	}

	public static CartId from(final long id) {
		return new CartId(id);
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
		final CartId cartId = (CartId)o;
		return id == cartId.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
