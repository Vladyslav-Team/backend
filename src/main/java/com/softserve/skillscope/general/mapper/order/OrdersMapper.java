package com.softserve.skillscope.general.mapper.order;

import com.softserve.skillscope.security.payment.model.dto.OrderModel;
import com.softserve.skillscope.security.payment.model.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    OrderModel toSponsorOrders(Orders orders);
}
