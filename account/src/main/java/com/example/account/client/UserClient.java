package com.example.account.client;

import com.example.account.service.dto.UserDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gateway")
public interface UserClient {

    @GetMapping("/api/gateway/user")
    UserDetails getUserDetails(@RequestParam Long id);
}
