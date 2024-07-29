package shppingmall.commerce.image.entity;

import jakarta.persistence.*;
import lombok.*;
import shppingmall.commerce.product.entity.Product;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uploadName;
    private String fullPathUrl;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private Long targetId;


}
