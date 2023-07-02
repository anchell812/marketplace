package ru.inno.market;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.inno.market.core.Catalog;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.*;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class MarketServiceTest {

    private MarketService service;

    private Catalog catalog = new Catalog();
    private static Client CLIENT = new Client(1, "Anna");

    private final static Item ITEM = new Item(1, "iPhone 13", Category.SMARTPHONES, 50000);



    @BeforeEach
    public void setUp() {
        service = new MarketService();
    }

    @Test
    @DisplayName("Создание валидного заказа для клиента. Заказ создан")
    public void checkСreateValidOrderForClient() {
        int newId = service.createOrderFor(CLIENT);
        assertEquals(1, newId);
    };

    @Test
    @DisplayName("Создание заказа для клиента null. Заказ не создан")
    public void checkСreateOrderForClientNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createOrderFor(null));
    }

    @Test
    @DisplayName("Добавление товара к заказу")
    public void checkAddItemToOrder() {
        int newId = service.createOrderFor(CLIENT);
        service.addItemToOrder(ITEM,newId);
        service.addItemToOrder(ITEM,newId);
        assertEquals(100000, service.getOrderInfo(newId).getTotalPrice());
    }
    @Test
    @DisplayName("Добавление товара к заказу больше, чем есть на складе")
    public void checkAddMoreMatebookToOrder() {
        int newId = service.createOrderFor(CLIENT);
        Item matebook = catalog.getItemById(7);
        service.addItemToOrder(matebook,newId);
        service.addItemToOrder(matebook,newId);
        assertThrows(NoSuchElementException.class,
                () -> service.addItemToOrder(matebook,newId));

    }

    @Test
    @DisplayName("Добавление товара null к заказу")
    public void checkAddItemNullToOrder() {
        int newId = service.createOrderFor(CLIENT);
        assertThrows(IllegalArgumentException.class,
                () -> service.addItemToOrder(null, newId));
    }

    @DisplayName("Применение промокода")
    @ParameterizedTest(name = "{index} => Добавление промокода {0}")
    @EnumSource(PromoCodes.class)
    public void checkApplyDiscountForOrder(PromoCodes codes) {
        int newId = service.createOrderFor(CLIENT);
        service.addItemToOrder(ITEM,newId);
        double sum = service.getOrderInfo(newId).getTotalPrice() * (1 - codes.getDiscount());
        service.applyDiscountForOrder(newId, codes);
        assertTrue(service.getOrderInfo(newId).isDiscountApplied());
        assertEquals(sum, service.getOrderInfo(newId).getTotalPrice());
    }

}
