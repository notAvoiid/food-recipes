package com.food.recipes.controllers;

import com.food.recipes.model.dto.security.AuthenticationRequest;
import com.food.recipes.model.dto.security.AuthenticationResponse;
import com.food.recipes.model.dto.security.RegisterRequest;
import com.food.recipes.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoint for managing authentication")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @Operation(summary = "Adds a new User!",
            description = "Adds a new User by passing his data!",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        var register = service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponse(register.getName(), register.getToken()));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticates an User!",
            description = "Authenticates an User!",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<?> login(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        var login = service.login(request);
        return ResponseEntity.ok(new AuthenticationResponse(login.getName(), login.getToken()));
    }

}
