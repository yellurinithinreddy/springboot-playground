package com.nithin.order_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "shipping-service", path = "/shippings",url = "${SHIPPING_SERVICE_URI:}")
public interface ShippingOpenFeignClient {

    @GetMapping("/core/status")
    String getShippingStatus();
}
