package io.raineri.statistics.service.infrastructure.config;

import io.raineri.statistics.service.application.gateway.TransactionGateway;
import io.raineri.statistics.service.application.usecase.AddTransaction;
import io.raineri.statistics.service.application.usecase.RemoveTransaction;
import io.raineri.statistics.service.application.usecase.contract.AddTransactionImpl;
import io.raineri.statistics.service.application.usecase.contract.RemoveTransactionImpl;
import io.raineri.statistics.service.infrastructure.gateway.TransactionGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationWiringConfig {

    @Bean
    public TransactionGateway transactionGateway() {
        return new TransactionGatewayImpl();
    }

    @Bean
    public AddTransactionImpl addTransaction(TransactionGateway transactionGateway) {
        return new AddTransaction(transactionGateway);
    }

    @Bean
    public RemoveTransactionImpl removeTransaction(TransactionGateway transactionGateway) {
        return new RemoveTransaction(transactionGateway);
    }
}
