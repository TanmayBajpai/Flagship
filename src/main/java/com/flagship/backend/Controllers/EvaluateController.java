package com.flagship.backend.Controllers;

import com.flagship.backend.Services.EvaluateUserService;
import com.flagship.backend.Services.SuccessHandlerService;
import com.flagship.backend.Services.ValidateOwnershipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EvaluateController {

    private final ValidateOwnershipService validateOwnershipService;
    private final EvaluateUserService evaluateUserService;
    private final SuccessHandlerService successHandlerService;

    public EvaluateController(ValidateOwnershipService validateOwnershipService, EvaluateUserService evaluateUserService, SuccessHandlerService successHandlerService) {
        this.validateOwnershipService = validateOwnershipService;
        this.evaluateUserService = evaluateUserService;
        this.successHandlerService = successHandlerService;
    }

    @GetMapping("/evaluate/{id}")
    public ResponseEntity<?> evaluate(@RequestHeader("X-API-Key") String apiKey, @PathVariable String id, @RequestParam String userId, @RequestParam(required = false) String userCountry) {
        UUID uuid = UUID.fromString(id);
        validateOwnershipService.validate(apiKey, uuid);

        boolean response = evaluateUserService.evaluate(uuid, userId, userCountry);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/success")
    public ResponseEntity<?> success(@RequestHeader("X-API-Key") String apiKey, @RequestParam String userId) {

        successHandlerService.handleSuccess(apiKey, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Success handled");
    }

}
