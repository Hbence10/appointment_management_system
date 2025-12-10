package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody JsonNode loginBody) {
        return userService.login(loginBody.get("username").asText(), loginBody.get("password").asText());
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PostMapping("/register")
    public ResponseEntity<Object> registration(@RequestBody Users newUser) {
        return userService.register(newUser);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody JsonNode requestBody) {
        return userService.updateUser(id, requestBody.get("email").asText(), requestBody.get("username").asText());
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    //PFP
    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PatchMapping("/changePfp/{id}")
    public ResponseEntity<Users> changePfp(@PathVariable("id") Long id, @RequestParam("pfpImg") MultipartFile file) {
        return userService.changePfp(id, file);
    }

    //Adminok kezelese
    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("")
    public ResponseEntity<Object> getShortUsersList(){
        return userService.getShortUsersList();
    }

    //password-reset
    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/getVerificationCode")
    public ResponseEntity<String> getVerificationCode(@RequestParam("email") String email) {
        return userService.getVerificationCode(email);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody JsonNode requestBody) {
        return userService.checkVerificationCode(requestBody.get("vCode").asText(null), requestBody.get("email").asText(null));
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PatchMapping("/passwordReset")
    public ResponseEntity<HashMap<String, String>> updatePassword(@RequestBody JsonNode body) {
        HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("result", userService.updatePassword(body.get("email").asText(), body.get("newPassword").asText()).getBody());
        return ResponseEntity.ok(returnObject);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    //Error lekezelesek:
    @ExceptionHandler
    public ResponseEntity<String> handleUniqueError(DataIntegrityViolationException e) {
        String errorMsg = "";

        if (e.getMessage().contains("key 'email'")) {
            errorMsg = "duplicateEmail";
        } else {
            errorMsg = "duplicateUsername";
        }

        return ResponseEntity.status(409).body(errorMsg);
    }
}