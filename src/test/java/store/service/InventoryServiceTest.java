package store.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.model.Inventory;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    @Test
    void 상품_프로모션_파일_입력_테스트() {
        Inventory inventory = new Inventory();
        InventoryService inventoryService = new InventoryService(inventory);
        inventoryService.loadInventoryFromFile();
        System.out.println(inventory);
        Assertions.assertThat(inventory.productSize()).isEqualTo(11);
        Assertions.assertThat(inventory.promotionSize()).isEqualTo(3);
    }
}