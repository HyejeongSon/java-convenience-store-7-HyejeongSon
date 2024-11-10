package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void 재고_감소_확인_테스트() {
        Product product1 = new Product("콜라", 1000, 10, null, 0);
        product1.decreaseGeneralQuantity(3);
        Assertions.assertThat(product1.getGeneralQuantity()).isEqualTo(7);

        Product product2 = new Product("콜라", 1000, 10, "탄산2+1", 10);
        product2.decreasePromotionQuantity(4);
        Assertions.assertThat(product2.getPromotionQuantity()).isEqualTo(6);
    }
}