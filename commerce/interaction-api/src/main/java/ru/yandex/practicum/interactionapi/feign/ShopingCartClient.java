package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.request.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "shopping-cart", path = "/api/v1/shopping-cart")
public interface ShopingCartClient {

    @GetMapping
    ShoppingCartDto getShoppingCart(@RequestParam String username);

    @PutMapping
    ShoppingCartDto addProductToShoppingCart(@RequestParam String username,
                                                    @RequestBody Map<UUID, Long> request);
    @DeleteMapping
    void deactivateCurrentShoppingCart(@RequestParam String username);

    @PostMapping("/remove")
    ShoppingCartDto removeFromShoppingCart(@RequestParam String username,
                                                  @RequestBody Map<UUID, Long> request);

    @PostMapping("/change-quantity")
    ShoppingCartDto changeProductQuantity(@RequestParam String username, @RequestBody @Valid ChangeProductQuantityRequest requestDto);
}
