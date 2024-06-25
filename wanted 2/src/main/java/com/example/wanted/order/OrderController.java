package com.example.wanted.order;

import com.example.wanted.member.Member;
import com.example.wanted.product.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto postDto) {

        Order order = orderMapper.orderPostDtoToOrder(postDto);

        Order response = orderService.createOrder(order);

        return new ResponseEntity(orderMapper.orderToOrderResponseDto(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(@PathVariable("order-id") long orderId,
                                       @Valid @RequestBody OrderPatchDto patchDto){

        patchDto.setOrderId(orderId);

        Order response = orderService.updateOrder(orderMapper.orderPatchDtoToOrder(patchDto));

        return new ResponseEntity(orderMapper.orderToOrderResponseDto(response), HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") long orderId){

        Order response = orderService.findOrder(orderId);

        return new ResponseEntity(orderMapper.orderToOrderResponseDto(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size){

        Page<Order> orders = orderService.findOrders(page, size);

        List<OrderResponseDto> response = orders.stream()
                .map(order -> orderMapper.orderToOrderResponseDto(order))
                .collect(Collectors.toList());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    //구매 내역 조회(내가 구매자)
    @GetMapping("/{member-id}/purchased")
    public ResponseEntity getPurchasedOrder(@PathVariable("member-id") long memberId){
        List<Order> orders = orderService.findPurchasedOrders(memberId);

        List<OrderResponseDto> response = orders.stream()
                .map(order -> orderMapper.orderToOrderResponseDto(order))
                .collect(Collectors.toList());

        return new ResponseEntity(response, HttpStatus.OK);
    }


    //예약 내역 조회(내가 구매자/판매자 모두)
    @GetMapping("/{member-id}/reserved")
    public ResponseEntity gerReservedOrders(@PathVariable("member-id") long memberId){
        List<Order> orders = orderService.findReservedOrders(memberId);

        List<OrderResponseDto> response = orders.stream()
                .map(order -> orderMapper.orderToOrderResponseDto(order))
                .collect(Collectors.toList());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrder(@PathVariable("order-id") @Positive long orderId){

        orderService.deleteOrder(orderId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //거래 완료
    @PostMapping("/{order-id}/approve")
    public ResponseEntity approvedOrder(@PathVariable("order-id") long orderId){

        Order findOrder = orderService.findOrder(orderId);

        Member seller = findOrder.getProduct().getSeller();

        Order order = orderService.approveOrder(orderId, seller.getMemberId());

        return new ResponseEntity(orderMapper.orderToOrderResponseDto(order), HttpStatus.OK);
    }
}
