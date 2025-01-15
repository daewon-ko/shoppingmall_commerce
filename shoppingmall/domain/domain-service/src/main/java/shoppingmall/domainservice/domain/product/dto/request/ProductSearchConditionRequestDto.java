package shoppingmall.domainservice.domain.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.image.entity.FileType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProductSearchConditionRequestDto {
    private Long categoryId;
    private Integer minPrice;
    private Integer maxPrice;
    private String productName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<FileType> fileTypes;

    @Builder
    protected ProductSearchConditionRequestDto(Long categoryId, Integer minPrice, Integer maxPrice, String productName, LocalDateTime startDate, LocalDateTime endDate, List<FileType> fileTypes) {
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.productName = productName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileTypes = fileTypes;
    }
}
