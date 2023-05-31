package com.softserve.skillscope.security.payment.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.mapper.order.OrdersMapper;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.security.payment.OrdersRepository;
import com.softserve.skillscope.security.payment.model.dto.OrderModel;
import com.softserve.skillscope.security.payment.model.dto.OrdersResponse;
import com.softserve.skillscope.security.payment.model.entity.Orders;
import com.softserve.skillscope.security.payment.service.OrdersService;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private UtilService utilService;
    private OrdersRepository orderRepo;
    private OrdersMapper ordersMapper;

    @Override
    public OrdersResponse getAllOrders(Long sponsorId, int page, int size) {
        Sponsor sponsor = utilService.findSponsorById(sponsorId);
        if (utilService.isNotCurrentUser(sponsor.getUser())) {
            throw new ForbiddenRequestException();
        }
        if (page < 1 || size < 1) {
            throw new BadRequestException("Page index/size must not be lower than expected");
        }
        Page<Orders> pageSponsors =
                orderRepo.findAllBySponsorOrderByCreateDate(PageRequest.of(page - 1, size), sponsor);
        if (pageSponsors.isEmpty())
            return OrdersResponse.builder().id(sponsorId).status("No orders were found. Try next time.").build();

        int totalPages = pageSponsors.getTotalPages();

        List<OrderModel> sponsors = new ArrayList<>(pageSponsors.stream()
                .map(ordersMapper::toSponsorOrders)
                .toList());

        return OrdersResponse.builder()
                .totalItems(pageSponsors.getTotalElements())
                .totalPage(totalPages)
                .currentPage(page)
                .orders(sponsors)
                .build();
    }
}
