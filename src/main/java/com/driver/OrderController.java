package com.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
        return ResponseEntity.ok("Order added successfully.");
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId) {
        orderService.addPartner(partnerId);
        return ResponseEntity.ok("Partner added successfully.");
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> assignOrderToPartner(@RequestParam String orderId, @RequestParam String partnerId) {
        orderService.assignOrderToPartner(orderId, partnerId);
        return ResponseEntity.ok("Order assigned to partner successfully.");
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId) {
        DeliveryPartner partner = orderService.getPartnerById(partnerId);
        return ResponseEntity.ok(partner);
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId) {
        int count = orderService.getOrderCountByPartnerId(partnerId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId) {
        List<String> orders = orderService.getOrdersByPartnerId(partnerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getUnassignedOrderCount() {
        int count = orderService.getUnassignedOrderCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{time}/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTime(@PathVariable String time, @PathVariable String partnerId) {
        int count = orderService.getOrdersLeftAfterGivenTime(time, partnerId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTime(@PathVariable String partnerId) {
        String time = orderService.getLastDeliveryTime(partnerId);
        return ResponseEntity.ok(time);
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId) {
        orderService.deletePartnerById(partnerId);
        return ResponseEntity.ok("Partner deleted successfully.");
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
