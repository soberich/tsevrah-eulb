package com.example.account.client;

import com.example.account.service.dto.UserDetailedView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Profile("prod,dev")
@FeignClient(name = "gateway")
public interface UserClient {

    @GetMapping("/api/gateway/user")
    UserDetailedView getUserDetails(@RequestParam Long customerIdAkaUserId);
}
