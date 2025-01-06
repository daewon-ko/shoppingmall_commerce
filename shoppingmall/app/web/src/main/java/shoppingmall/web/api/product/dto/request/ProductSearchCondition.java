package shoppingmall.web.api.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.image.entity.FileType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProductSearchCondition {
    private Long categoryId;
    @Min(value = 0L, message = "최소 0원 이상이어야 합니다.")
    private Integer minPrice;
    @Max(value = 999999999L, message = "최대 금액은 10억 이하입니다.")
    private Integer maxPrice;
    private String productName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<FileType> fileTypes;

    @Builder
    protected ProductSearchCondition(Long categoryId, Integer minPrice, Integer maxPrice, String productName, LocalDateTime startDate, LocalDateTime endDate, List<FileType> fileTypes) {
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.productName = productName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileTypes = fileTypes;
    }
}
