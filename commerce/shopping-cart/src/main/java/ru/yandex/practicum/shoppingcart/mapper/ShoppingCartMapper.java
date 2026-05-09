package ru.yandex.practicum.shoppingcart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.shoppingcart.model.ShoppingCart;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoppingCartMapper {

    ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart);
}
