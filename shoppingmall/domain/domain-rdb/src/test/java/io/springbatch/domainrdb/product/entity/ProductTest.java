package io.springbatch.domainrdb.product.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품의 이름과 가격을 수정할 수 있다.")
    @Test
    void updateDetails() {
        //given
        Product productA = createProduct(10000, "상품A");
        //when
        Product changedProduct = productA.updateDetails("상품B", 5000);
        //then
        Assertions.assertThat(changedProduct)
                .extracting("price", "name")
                .containsExactly(5000, "상품B");
    }


    private static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }

}