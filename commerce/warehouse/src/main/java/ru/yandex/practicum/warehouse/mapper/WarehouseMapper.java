package ru.yandex.practicum.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.model.Warehouse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseMapper {
    Warehouse toWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest);
}
