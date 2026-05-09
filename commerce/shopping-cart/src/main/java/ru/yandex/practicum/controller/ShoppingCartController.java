package ru.yandex.practicum.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
@Validated
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto getShoppingCart(@RequestParam @NotBlank String username) {
        return shoppingCartService.getShoppingCart(username);
    }

    @PutMapping
    public ShoppingCartDto addProductToShoppingCart(@RequestParam @NotBlank String username,
                                                    @RequestBody Map<UUID, Integer> request) {
        return shoppingCartService.addProductToShoppingCart(username, request);
    }

    @DeleteMapping
    public void deactivateCurrentShoppingCart(@RequestParam @NotBlank String username) {
        shoppingCartService.deactivateCurrentShoppingCart(username);
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeFromShoppingCart(@RequestParam @NotBlank String username,
                                                  @RequestBody List<UUID> productsId) {
        return shoppingCartService.removeFromShoppingCart(username, productsId);
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeProductQuantity(@RequestParam @NotBlank String username,
                                                 @RequestBody ChangeProductQuantityRequest requestDto) {
        return shoppingCartService.changeProductQuantity(username, requestDto);
    }

    @PostMapping("/booking")
    public BookedProductsDto bookingProducts(@RequestParam @NotBlank String username) {
        return shoppingCartService.bookingProducts(username);
    }
}