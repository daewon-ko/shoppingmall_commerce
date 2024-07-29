package shppingmall.commerce.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.image.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
