package shppingmall.commerce.image.entity;

import jakarta.persistence.*;
import lombok.*;
import shppingmall.commerce.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uploadName;
    private String fullPathUrl;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private Long targetId;
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;


    private LocalDateTime deletedAt;


    public void deleteImage(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        this.isDeleted = true;
    }
}
