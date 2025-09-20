package com.flagship.backend.Controllers;

import com.flagship.backend.DTO.CreateFlagRequest;
import com.flagship.backend.DTO.UpdateFlagRequest;
import com.flagship.backend.Entities.FeatureFlag;
import com.flagship.backend.Respositories.FeatureFlagRepository;
import com.flagship.backend.Services.*;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/flags")
public class FlagController {

    private final CreateFlagService createFlagService;
    private final UpdateFlagService updateFlagService;
    private final DeleteFlagService deleteFlagService;
    private final ResetUsersService resetUsersService;
    private final ValidateOwnershipService validateOwnershipService;
    private final FeatureFlagRepository featureFlagRepository;

    public FlagController(CreateFlagService createFlagService, UpdateFlagService updateFlagService, DeleteFlagService deleteFlagService, ResetUsersService resetUsersService, ValidateOwnershipService validateOwnershipService, FeatureFlagRepository featureFlagRepository) {
        this.createFlagService = createFlagService;
        this.updateFlagService = updateFlagService;
        this.deleteFlagService = deleteFlagService;
        this.resetUsersService = resetUsersService;
        this.validateOwnershipService = validateOwnershipService;
        this.featureFlagRepository = featureFlagRepository;
    }

    @PostMapping("/create-flag")
    public ResponseEntity<String> createFlag(@Valid @RequestBody CreateFlagRequest createFlagRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        createFlagService.createFlag(createFlagRequest, username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Flag created");
    }

    @PutMapping("/update-flag/{id}")
    public ResponseEntity<String> updateFlag(@RequestBody UpdateFlagRequest updateFlagRequest, @PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("delete flag called");
        String username = userDetails.getUsername();
        validateOwnershipService.validateByUsername(username, id);

        updateFlagService.updateFlag(updateFlagRequest, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Flag updated");
    }

    @DeleteMapping("/delete-flag/{id}")
    public ResponseEntity<String> deleteFlag(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        validateOwnershipService.validateByUsername(username, id);

        deleteFlagService.deleteFlag(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Flag deleted");
    }

    @PutMapping("/reset-users/{id}")
    public ResponseEntity<String> resetUsers(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        validateOwnershipService.validateByUsername(username, id);

        resetUsersService.resetUsers(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Flag seed reset");
    }

    @GetMapping("/get-flags")
    public ResponseEntity<?> getFlags(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        List<FeatureFlag> featureFlags = featureFlagRepository.findFeatureFlagsByOwner(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(featureFlags);
    }
}
