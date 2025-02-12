package shoppingmall.domainservice.domain.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.domainrdb.user.entity.User;

/**
 * 상품을 생성을 요청하는 DTO입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {
    
    private String name;
    private int price;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonProperty("sellerEmail")
    private String sellerEmail;

}
