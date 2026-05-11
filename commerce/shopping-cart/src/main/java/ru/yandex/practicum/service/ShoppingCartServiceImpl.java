package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.mapper.ShoppingCartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.ShoppingCartRepository;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final WarehouseClient warehouseClient;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getShoppingCart(String username) {
        checkUsername(username);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUsername(username);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto addProductToShoppingCart(String username, Map<UUID, Long> request) {
        checkUsername(username);
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .username(username)
                .products(request)
                .active(true)
                .build();
        return shoppingCartMapper.toShoppingCartDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public void deactivateCurrentShoppingCart(String username) {
        checkUsername(username);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUsername(username);
        shoppingCart.setActive(false);
    }

    @Override
    public ShoppingCartDto removeFromShoppingCart(String username, Map<UUID, Long> request) {
        checkUsername(username);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUsername(username);
        if (shoppingCart == null) {
            throw new NoProductsInShoppingCartException("Пользователь " + username + " не имеет корзину покупок.");
        }
        shoppingCart.setProducts(request);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest requestDto) {
        checkUsername(username);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUsername(username);
        shoppingCart.getProducts().entrySet().stream()
                .filter(entry -> entry.getKey().equals(requestDto.getProductId()))
                .peek(entry -> entry.setValue(requestDto.getNewQuantity()))
                .findAny()
                .orElseThrow(() -> new NoProductsInShoppingCartException("Пользователь " + username + " не имеет корзину покупок."));
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public BookedProductsDto bookingProductsForUser(String username) {
        checkUsername(username);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUsername(username);
        return warehouseClient.bookingProducts(shoppingCartMapper.toShoppingCartDto(shoppingCart));
    }

    private void checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new NotAuthorizedUserException("Имя пользователя не должно быть пустым.");
        }
    }
}