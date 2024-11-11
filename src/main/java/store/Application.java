package store;

import store.model.Inventory;
import store.service.InventoryService;
import store.service.OrderService;

public class Application {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        InventoryService inventoryService = new InventoryService(inventory);
        inventoryService.loadInventoryFromFile();
        OrderService orderService = new OrderService(inventory);
        orderService.startOrderService();
    }
}
