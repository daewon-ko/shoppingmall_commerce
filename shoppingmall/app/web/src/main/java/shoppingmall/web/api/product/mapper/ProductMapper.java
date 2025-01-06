package shoppingmall.web.api.product.mapper;

import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.web.api.product.dto.request.ProductCreateRequestDto;

public class ProductMapper {

    public static CategoryDomain toCategoryDomain(ProductCreateRequestDto requestDto) {
        return new CategoryDomain(requestDto.getCagegoryName());
    }

    public static ProductDomain toProduct(ProductCreateRequestDto requestDto) {
        return new ProductDomain(requestDto.getName(), requestDto.getPrice(), toCategoryDomain(requestDto), toUserDomain(requestDto));
    }

    public static UserDomain toUserDomain(ProductCreateRequestDto requestDto) {
        return new UserDomain(null, requestDto.getSellerEmail());
    }
}
