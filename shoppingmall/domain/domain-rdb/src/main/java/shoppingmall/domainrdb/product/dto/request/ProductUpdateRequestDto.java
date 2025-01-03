package shoppingmall.domainrdb.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductUpdateRequestDto {

    @NotBlank(message = "이름은 공백이 될 수 없습니다.")
    private String name;
    @PositiveOrZero(message = "가격은 음수가 될 수 없습니다.")
    @Max(value = 9999999999L, message = "가격은 9999999999 이상일 수 없습니다.")
    private int price;

    private List<Long> imagesToDelete = new ArrayList<>();


    @Builder
    private ProductUpdateRequestDto(String name, int price, List<Long> imagesToDelete) {
        this.name = name;
        this.price = price;
        this.imagesToDelete = imagesToDelete;
    }

}
