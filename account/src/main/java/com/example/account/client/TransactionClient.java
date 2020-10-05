package com.example.account.client;

import com.example.account.web.rest.vm.SimpleTransaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "transaction")
public interface TransactionClient {

    @PostMapping("/api/monetary-transactions")
    ResponseEntity<Void> processTransaction(@Valid @RequestBody SimpleTransaction transaction);
}
