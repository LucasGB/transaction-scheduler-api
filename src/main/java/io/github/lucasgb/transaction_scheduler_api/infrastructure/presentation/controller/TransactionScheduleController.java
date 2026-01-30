package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.controller;

import io.github.lucasgb.transaction_scheduler_api.application.command.AddTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.command.DeleteTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.command.FetchTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.command.UpdateTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.dto.AddTransactionScheduleCommandResult;
import io.github.lucasgb.transaction_scheduler_api.application.dto.FetchTransactionScheduleCommandResult;
import io.github.lucasgb.transaction_scheduler_api.application.dto.UpdateTransactionScheduleCommandResult;
import io.github.lucasgb.transaction_scheduler_api.application.dto.request.AddTransactionScheduleRequest;
import io.github.lucasgb.transaction_scheduler_api.application.dto.request.FetchTransactionScheduleRequest;
import io.github.lucasgb.transaction_scheduler_api.application.dto.request.UpdateTransactionScheduleRequest;
import io.github.lucasgb.transaction_scheduler_api.application.handler.AddTransactionScheduleCommandHandler;
import io.github.lucasgb.transaction_scheduler_api.application.handler.DeleteTransactionScheduleCommandHandler;
import io.github.lucasgb.transaction_scheduler_api.application.handler.FetchTransactionScheduleCommandHandler;
import io.github.lucasgb.transaction_scheduler_api.application.handler.UpdateTransactionScheduleCommandHandler;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

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

    @PostMapping("/create")
    public ResponseEntity<?> addTransaction(@Valid @RequestBody AddTransactionScheduleRequest request) {
        final AddTransactionScheduleCommand command = AddTransactionScheduleCommand.fromRequest(request);
        final AddTransactionScheduleCommandResult result = addTransactionScheduleCommandHandler.handle(command);

        if (!result.sucess()) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "error", result.errorMessage()
                    )
            );
        }

        return ResponseEntity.ok(result.transactionSchedule());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionScheduleRequest request) {

        final UpdateTransactionScheduleCommand command = UpdateTransactionScheduleCommand.fromRequest(id, request);

        if (command.newTransferAmount() == null && command.scheduleDate() == null) {
            return ResponseEntity.badRequest()
                    .body("At least one of 'newTransferAmount' or 'scheduleDate' must be provided.");
        }

        final UpdateTransactionScheduleCommandResult result = updateTransactionScheduleCommandHandler.handle(command);

        if (!result.success()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", result.errorMessage())
            );
        }

        return ResponseEntity.ok(result.transactionSchedule());
    }

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
        final FetchTransactionScheduleCommand query = FetchTransactionScheduleCommand.fromRequest(request);
        final FetchTransactionScheduleCommandResult result = fetchTransactionScheduleQueryHandler.handle(query);

        return ResponseEntity.ok(result.transactions());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        final DeleteTransactionScheduleCommand command = new DeleteTransactionScheduleCommand(id);
        final var result = deleteTransactionScheduleCommandHandler.handle(command);

        if (!result.success()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", result.errorMessage())
            );
        }

        return ResponseEntity.noContent().build();
    }
}
