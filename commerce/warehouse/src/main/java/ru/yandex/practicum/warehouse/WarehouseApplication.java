package ru.yandex.practicum.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.interactionapi.feign.ShopingStoreClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients = {ShopingStoreClient.class})
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

}
