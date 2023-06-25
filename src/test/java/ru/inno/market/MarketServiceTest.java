package ru.inno.market;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.Client;
import ru.inno.market.model.Order;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MarketServiceTest {

    private MarketService service = new MarketService();
    private static Client CLIENT = new Client(1, "Anna");

    @Test
    @DisplayName("Создание валидного заказа для клиента. Заказ создан")
    public void checkСreateValidOrderForClient() {
        int newOrder = service.createOrderFor(CLIENT);
        assertEquals(1, newOrder);
    };

    @Test
    @DisplayName("Создание заказа для клиента null. Заказ не создан")
    public void checkСreateOrderForClientNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createOrderFor(null));
    }

}
