package io.github.lucasgb.transaction_scheduler_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private String serverPort;

    @Bean
    public OpenAPI transactionSchedulerOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.example.com")
                                .description("Production Server (Example)")
                ))
                .tags(List.of(
                        new Tag()
                                .name("Transaction Schedule")
                                .description("Operations for managing scheduled financial transactions")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title("Transaction Scheduler API")
                .version("1.0.0")
                .description("""
                        A financial transaction scheduling system that allows users to schedule money transfers 
                        between accounts with automatic fee calculation based on transfer amount and scheduling date.
                        
                        ## Features
                        - **Automated Fee Calculation**: Fees are calculated automatically based on predefined business rules
                        - **Flexible Scheduling**: Schedule transactions from today up to 1 year in the future
                        - **Dynamic Filtering**: Query transactions by account, date range with pagination
                        - **Update Capabilities**: Modify amount or schedule date with automatic fee recalculation
                        
                        ## Fee Calculation Rules
                        
                        The system applies the following fee tiers:
                        
                        ### Taxa A (€0 - €1,000)
                        - **Same day (0 days)**: 3% + €3 fixed fee
                        
                        ### Taxa B (€1,001 - €2,000)
                        - **1-10 days**: 9%
                        
                        ### Taxa C (>€2,000)
                        - **11-20 days**: 8.2%
                        - **21-30 days**: 6.9%
                        - **31-40 days**: 4.7%
                        - **41+ days**: 1.7%
                        
                        ## Business Concepts
                        
                        - **Total Amount**: The gross amount you want to transfer (input)
                        - **Fee Amount**: Calculated fee based on rules above
                        - **Net Amount**: What the recipient receives (total - fee)
                        """)
                .contact(new Contact()
                        .name("API Support")
                        .email("support@example.com")
                        .url("https://github.com/lucasgb/transaction-scheduler-api"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));
    }
}
