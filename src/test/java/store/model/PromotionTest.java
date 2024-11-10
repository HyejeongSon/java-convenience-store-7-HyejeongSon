package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    @Test
    void 프로모션_범위_날짜_확인_테스트() {
        // 탄산2+1,2,1,2024-01-01,2024-12-31
        Promotion promotion = new Promotion("탄산2+1", 2, 1,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
        boolean dateInRange = promotion.isDateInRange(LocalDate.now());
        Assertions.assertThat(dateInRange).isEqualTo(true);
    }

}