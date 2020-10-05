package com.example.account.client;

import com.example.account.service.dto.AccountTransaction;
import com.example.account.service.dto.SimpleTransaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@FeignClient(name = "transaction")
public interface TransactionClient {

    @PostMapping("/api/monetary-transactions")
    AccountTransaction processTransaction(@Valid @RequestBody SimpleTransaction transaction);

    @GetMapping("/api/my-monetary-transactions/{customerID}")
    List<AccountTransaction> getAllTransactionsForCustomer(@NotNull @PathVariable Long customerID);
}
