package shoppingmall.domainservice.domain.product.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductUpdateRequestDto {

    private String name;
    private int price;
    // TODO : 변경할 이미지 번호가 requestDTO에 들어가는 것이 과연 적절할까?
    private List<Long> imagesToDelete = new ArrayList<>();


    @Builder
    private ProductUpdateRequestDto(String name, int price, List<Long> imagesToDelete) {
        this.name = name;
        this.price = price;
        this.imagesToDelete = imagesToDelete;
    }

}
