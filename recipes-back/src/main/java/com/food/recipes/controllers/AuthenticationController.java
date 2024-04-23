package com.food.recipes.controllers;

import com.food.recipes.exceptions.ErrorMessage;
import com.food.recipes.model.dto.security.AuthenticationRequest;
import com.food.recipes.model.dto.security.AuthenticationResponse;
import com.food.recipes.model.dto.security.RegisterRequest;
import com.food.recipes.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
            @RequestBody @Valid RegisterRequest request,
            HttpServletRequest httpServletRequest
    ) {
        try {
            var register = service.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponse(register.getName(), register.getToken()));
        } catch (DataIntegrityViolationException ex) {
            log.info("The username: {} already exists", request.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage
                    (httpServletRequest, HttpStatus.CONFLICT,
                            new Date(), "The username already exists!!"));
        } catch (Exception ex) {
            log.error("API Error - ", ex);
        }
        return ResponseEntity.badRequest().body(new ErrorMessage
                (httpServletRequest, HttpStatus.BAD_REQUEST,
                        new Date(), "Try again later!!"));

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
            @RequestBody @Valid AuthenticationRequest request,
            HttpServletRequest httpServletRequest
    ) {
        try {
            var login = service.login(request);
            return ResponseEntity.ok(new AuthenticationResponse(login.getName(), login.getToken()));
        } catch (AuthenticationException ex) {
            log.warn("Bad credentials from username: {}", request.getEmail());
        } catch (Exception ex) {
            log.error("API Error - ", ex);
        }
        return ResponseEntity.badRequest().body(new ErrorMessage
                (httpServletRequest, HttpStatus.BAD_REQUEST,
                        new Date(), "Username or password incorrect! Try again!"));
    }

}
