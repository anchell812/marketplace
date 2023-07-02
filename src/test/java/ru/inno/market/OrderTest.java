package ru.inno.market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.inno.market.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private final static Client CLIENT = new Client(1, "Anna");
    private final static Item ITEM1 = new Item(1, "iPhone 13", Category.SMARTPHONES, 50000);
    private final static Item ITEM2 = new Item(2, "iPhone 14", Category.SMARTPHONES, 70000);

    @Test
    @DisplayName("Создание заказа с валидными данными")
    public void checkCreateOrder () {
        Order order = new Order(1, CLIENT);
        assertEquals(1, order.getId());
    }

    @Test
    @DisplayName("Добавление товара к заказу")
    public void checkAddItem() {
        Order order = new Order(1, CLIENT);
        order.addItem(ITEM1);
        order.addItem(ITEM2);
        assertEquals(120000, order.getTotalPrice());
    }

    @Test
    @DisplayName("Добавление товара null к заказу")
    public void checkAddItemNull() {
        Order order = new Order(1, CLIENT);
        assertThrows(IllegalArgumentException.class, () -> order.addItem(null));
    }


    @Test
    @DisplayName("Применение валидной скидки")
    public void checkApplyDiscount() {
        Order order = new Order(1, CLIENT);
        order.addItem(ITEM1);
        order.applyDiscount(PromoCodes.FIRST_ORDER.getDiscount());
        assertTrue(order.isDiscountApplied());
        assertEquals(40000, order.getTotalPrice());
    }


    @DisplayName("Применение невалидных значений скидок")
    @ParameterizedTest(name = "{index} => Применение скидки {0}")
    @ValueSource(doubles = {-10, -1, 0, 1, 10})
    public void checkApplyNegativeDiscountValues(double discount) {
        Order order = new Order(1, CLIENT);
        order.addItem(ITEM1);
        assertThrows(IllegalArgumentException.class, () -> order.applyDiscount(discount));
    }
}
