package com.example.account.client;

import com.example.account.service.dto.TransactionView;
import com.example.account.service.dto.TransactionCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@Profile("prod,dev")
@FeignClient(name = "transaction")
public interface TransactionClient {

    @PostMapping("/api/monetary-transactions")
    TransactionView processTransaction(@Valid @RequestBody TransactionCommand transaction);

    @GetMapping("/api/my-monetary-transactions/{customerID}")
    List<TransactionView> getAllTransactionsForCustomer(@NotNull @PathVariable Long customerID);
}
