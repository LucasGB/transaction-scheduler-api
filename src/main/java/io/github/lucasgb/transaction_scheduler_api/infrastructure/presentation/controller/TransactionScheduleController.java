package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.controller;

import io.github.lucasgb.transaction_scheduler_api.application.command.AddTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.command.DeleteTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.command.FetchTransactionScheduleQuery;
import io.github.lucasgb.transaction_scheduler_api.application.command.UpdateTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response.FetchTransactionScheduleQueryResponse;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.request.AddTransactionScheduleRequest;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.request.FetchTransactionScheduleRequest;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.request.UpdateTransactionScheduleRequest;
import io.github.lucasgb.transaction_scheduler_api.application.service.handler.AddTransactionScheduleCommandHandler;
import io.github.lucasgb.transaction_scheduler_api.application.service.handler.DeleteTransactionScheduleCommandHandler;
import io.github.lucasgb.transaction_scheduler_api.application.service.handler.FetchTransactionScheduleCommandHandler;
import io.github.lucasgb.transaction_scheduler_api.application.service.handler.UpdateTransactionScheduleCommandHandler;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response.ApiError;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response.AddTransactionScheduleResponse;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response.UpdateTransactionScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Transaction Schedule", description = "Manage scheduled financial transactions")
@RestController
@RequestMapping("/api/v1/transaction-schedule")
public class TransactionScheduleController {

    private final AddTransactionScheduleCommandHandler addTransactionScheduleCommandHandler;
    private final FetchTransactionScheduleCommandHandler fetchTransactionScheduleQueryHandler;
    private final UpdateTransactionScheduleCommandHandler updateTransactionScheduleCommandHandler;
    private final DeleteTransactionScheduleCommandHandler deleteTransactionScheduleCommandHandler;

    public TransactionScheduleController(AddTransactionScheduleCommandHandler addTransactionScheduleCommandHandler, FetchTransactionScheduleCommandHandler fetchTransactionScheduleQueryHandler, UpdateTransactionScheduleCommandHandler updateTransactionScheduleCommandHandler, DeleteTransactionScheduleCommandHandler deleteTransactionScheduleCommandHandler) {
        this.addTransactionScheduleCommandHandler = addTransactionScheduleCommandHandler;
        this.fetchTransactionScheduleQueryHandler = fetchTransactionScheduleQueryHandler;
        this.updateTransactionScheduleCommandHandler = updateTransactionScheduleCommandHandler;
        this.deleteTransactionScheduleCommandHandler = deleteTransactionScheduleCommandHandler;
    }

    @Operation(
            summary = "Create a transaction schedule",
            description = "Schedules a financial transaction and calculates applicable fees"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction created successfully", content = @Content(schema = @Schema(implementation = AddTransactionScheduleResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Validation or business rule error", content = @Content(
                    schema = @Schema(implementation = ApiError.class),
                    examples = {
                            @ExampleObject(
                                    name = "Invalid Currency",
                                    value = "{\"code\": \"INVALID_ARGUMENT\", \"message\": \"[currency]: Currency must be EUR, USD, or BRL\"}"
                            ),
                            @ExampleObject(
                                    name = "Same account transfer",
                                    value = "{\"code\": \"SAME_ACCOUNT_TRANSFER_EXCEPTION\", \"message\": \"Source and target accounts must be different\"}"
                            ),
                            @ExampleObject(
                                    name = "No matching fee calculation rule",
                                    value = "{\"code\": \"NO_MATCHING_FEE_CALCULATION_RULE\", \"message\": \"No matching fee calculation rule.\"}"
                            ),

                    },
                    mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
                    schema = @Schema(implementation = ApiError.class), mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Unexpected Error",
                                    value = "{\"code\": \"INTERNAL_ERROR\", \"message\": \"An unexpected error occurred.\"}"
                            )
                    })
            )
    })
    @PostMapping("/create")
    public ResponseEntity<?> addTransaction(@Valid @RequestBody AddTransactionScheduleRequest request) {
        final AddTransactionScheduleCommand command = AddTransactionScheduleCommand.fromRequest(request);
        final AddTransactionScheduleResponse response = addTransactionScheduleCommandHandler.handle(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Update a scheduled transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction updated", content = @Content(schema = @Schema(implementation = UpdateTransactionScheduleResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Validation or business rule error", content = @Content(
                    schema = @Schema(implementation = ApiError.class),
                    examples = {
                            @ExampleObject(
                                    name = "Invalid Input",
                                    value = "{\"code\": \"INVALID_INPUT\", \"message\": \"At least one of 'newTransferAmount' or 'scheduleDate' must be provided.\"}"
                            ),
                            @ExampleObject(
                                    name = "Invalid Transfer Amount",
                                    value = "{\"code\": \"INVALID_ARGUMENT\", \"message\": \"[newTransferAmount]: Transfer amount must be greater than 0\"}"
                            )
                    },
                    mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", description = "Transaction not found", content = @Content(
                            schema = @Schema(implementation = ApiError.class),
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Transaction not found",
                                            value = "{\"code\": \"TRANSACTION_NOT_FOUND\", \"message\": \"Transaction not found\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
                    schema = @Schema(implementation = ApiError.class), mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Unexpected Error",
                                    value = "{\"code\": \"INTERNAL_ERROR\", \"message\": \"An unexpected error occurred.\"}"
                            )
                    })
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionScheduleRequest request) {

        final UpdateTransactionScheduleCommand command = UpdateTransactionScheduleCommand.fromRequest(id, request);

        if (!command.hasUpdates())
            throw new IllegalArgumentException("At least one of 'newTransferAmount' or 'scheduleDate' must be provided.");

        final UpdateTransactionScheduleResponse result = updateTransactionScheduleCommandHandler.handle(command);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Fetch scheduled transactions with optional filters")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of scheduled transactions matching the filters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FetchTransactionScheduleQueryResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Example response",
                                            value = """
                                                {
                                                  "page": 0,
                                                  "size": 20,
                                                  "totalItems": 2,
                                                  "totalPages": 1,
                                                  "items": [
                                                    {
                                                      "id": 1,
                                                      "netAmount": {
                                                        "amount": 1820.00,
                                                        "currency": "USD"
                                                      },
                                                      "feeAmount": {
                                                        "amount": 180.00,
                                                        "currency": "USD"
                                                      },
                                                      "totalAmount": {
                                                        "amount": 2000.00,
                                                        "currency": "USD"
                                                      },
                                                      "sourceAccount": "PT50000001",
                                                      "targetAccount": "PT50000002",
                                                      "scheduleDate": "2026-02-03",
                                                      "createdAt": "2026-02-02T02:47:01.12042",
                                                      "updatedAt": "2026-02-02T02:47:01.12044"
                                                    },
                                                    {
                                                      "id": 2,
                                                      "netAmount": {
                                                        "amount": 3680.00,
                                                        "currency": "USD"
                                                      },
                                                      "feeAmount": {
                                                        "amount": 320.00,
                                                        "currency": "USD"
                                                      },
                                                      "totalAmount": {
                                                        "amount": 4000.00,
                                                        "currency": "USD"
                                                      },
                                                      "sourceAccount": "PT50000001",
                                                      "targetAccount": "DE490000001",
                                                      "scheduleDate": "2026-02-15",
                                                      "createdAt": "2026-02-02T02:47:21.801804",
                                                      "updatedAt": "2026-02-02T02:47:21.801823"
                                                    }
                                                  ]
                                                }
                                            """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid query parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(
                                    value = "{\"code\": \"INVALID_DATE_RANGE\", \"message\": \"scheduleDateFrom cannot be after scheduleDateTo\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(
                                    value = "{\"code\": \"INTERNAL_ERROR\", \"message\": \"Something went wrong on the server\"}"
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<?> fetchTransactions(
            @RequestParam(required = false) String sourceAccount,
            @RequestParam(required = false) String targetAccount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scheduleDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scheduleDateTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        final FetchTransactionScheduleRequest request = new FetchTransactionScheduleRequest(
                sourceAccount, targetAccount, scheduleDateFrom, scheduleDateTo, page, size
        );
        final FetchTransactionScheduleQuery query = FetchTransactionScheduleQuery.fromRequest(request);
        final FetchTransactionScheduleQueryResponse result = fetchTransactionScheduleQueryHandler.handle(query);

        return ResponseEntity.ok(result.transactions());
    }

    @Operation(summary = "Delete a scheduled transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transaction deleted"),
            @ApiResponse(
                    responseCode = "404", description = "Transaction not found", content = @Content(
                    schema = @Schema(implementation = ApiError.class),
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Transaction not found",
                                    value = "{\"code\": \"TRANSACTION_NOT_FOUND\", \"message\": \"Transaction not found\"}"
                            )
                    })
            ),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        final DeleteTransactionScheduleCommand command = new DeleteTransactionScheduleCommand(id);
        deleteTransactionScheduleCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }
}
