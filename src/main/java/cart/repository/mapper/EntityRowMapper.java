package cart.repository.mapper;

import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.RowMapper;

public class EntityRowMapper {
    private EntityRowMapper() {
    }

    public static RowMapper<ProductEntity> productRowMapper() {
        return (rs, rowNum) -> ProductEntity.Builder.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getInt("price"))
                .imageUrl(rs.getString("image_url"))
                .build();
    }

    public static RowMapper<MemberEntity> memberRowMapper() {
        return (rs, rowNum) -> new MemberEntity(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"));
    }
}
