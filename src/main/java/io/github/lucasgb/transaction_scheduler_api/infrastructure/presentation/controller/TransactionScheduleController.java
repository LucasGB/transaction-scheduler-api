package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.controller;

import io.github.lucasgb.transaction_scheduler_api.application.command.AddTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.dto.AddTransactionScheduleCommandResult;
import io.github.lucasgb.transaction_scheduler_api.application.dto.request.AddTransactionScheduleRequest;
import io.github.lucasgb.transaction_scheduler_api.application.handler.AddTransactionScheduleCommandHandler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/transaction-schedule")
public class TransactionScheduleController {

    private final AddTransactionScheduleCommandHandler addTransactionScheduleCommandHandler;

    public TransactionScheduleController(AddTransactionScheduleCommandHandler addTransactionScheduleCommandHandler) {
        this.addTransactionScheduleCommandHandler = addTransactionScheduleCommandHandler;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addTransaction(@Valid @RequestBody AddTransactionScheduleRequest request) {
        final AddTransactionScheduleCommand command = AddTransactionScheduleCommand.fromRequest(request);
        final AddTransactionScheduleCommandResult result = addTransactionScheduleCommandHandler.handle(command);

        if (!result.sucess()) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "id", result.transactionSchedule().getId(),
                            "error", result.errorMessage()
                    )
            );
        }

        return ResponseEntity.ok(result.transactionSchedule());
    }
}
