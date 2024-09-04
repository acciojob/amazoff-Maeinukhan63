package com.driver;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private Map<String, Order> orderMap = new HashMap<>();
    private Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    private Map<String, String> orderPartnerMap = new HashMap<>(); // orderId -> partnerId
    private Map<String, List<String>> partnerOrderMap = new HashMap<>(); // partnerId -> List<orderId>

    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    public void addPartner(String partnerId) {
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void assignOrderToPartner(String orderId, String partnerId) {
        orderPartnerMap.put(orderId, partnerId);
        partnerOrderMap.computeIfAbsent(partnerId, k -> new ArrayList<>()).add(orderId);
    }

    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerMap.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId) {
        return partnerOrderMap.getOrDefault(partnerId, Collections.emptyList()).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrderMap.getOrDefault(partnerId, Collections.emptyList());
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    public int getUnassignedOrderCount() {
        return (int) orderMap.keySet().stream()
                .filter(orderId -> !orderPartnerMap.containsKey(orderId))
                .count();
    }

    public int getOrdersLeftAfterGivenTime(String time, String partnerId) {
        List<String> orders = partnerOrderMap.getOrDefault(partnerId, Collections.emptyList());
        return (int) orders.stream()
                .map(orderMap::get)
                .filter(order -> order.getDeliveryTime().compareTo(time) > 0)
                .count();
    }

    public String getLastDeliveryTime(String partnerId) {
        List<String> orders = partnerOrderMap.getOrDefault(partnerId, Collections.emptyList());
        return orders.stream()
                .map(orderMap::get)
                .max(Comparator.comparing(Order::getDeliveryTime))
                .map(Order::getDeliveryTime)
                .orElse("No deliveries yet");
    }

    public void deletePartnerById(String partnerId) {
        List<String> orders = partnerOrderMap.remove(partnerId);
        if (orders != null) {
            for (String orderId : orders) {
                orderPartnerMap.remove(orderId);
            }
        }
        partnerMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        String partnerId = orderPartnerMap.remove(orderId);
        if (partnerId != null) {
            partnerOrderMap.get(partnerId).remove(orderId);
        }
        orderMap.remove(orderId);
    }
}