package ru.yandex.practicum.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients = {WarehouseClient.class})
public class ShoppingCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartApplication.class, args);
    }

}
