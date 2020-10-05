package com.example.account.client;

import com.example.account.AccountApp;
import com.example.account.web.rest.vm.SimpleTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = AccountApp.class)
@AutoConfigureMockMvc
@WithMockUser
class TransactionClientIT {

    @Autowired
    private TransactionClient transactionClient;

    @Test
    void processTransaction() {
        assertThat(transactionClient).isNotNull();
//        assertThat(transactionClient)
//            .extracting(it -> it.processTransaction(new SimpleTransaction(1L, BigDecimal.TEN)))
//            .satisfies(it -> assertThat(it).extracting(ResponseEntity::getStatusCodeValue).isEqualTo(200));
    }
}
