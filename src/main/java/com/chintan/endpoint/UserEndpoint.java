package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chintan.dto.PasswordChangeRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Management", description = "APIs for user profile and password management")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @Operation(
        summary = "Get User Profile",
        description = "Retrieve the profile information of the currently logged-in user."
    )
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile();

    @Operation(
        summary = "Change Password",
        description = "Change the password of the currently logged-in user."
    )
    @PostMapping("/change-password")
    public ResponseEntity<?> ChangePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
}
