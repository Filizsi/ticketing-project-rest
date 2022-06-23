package com.cydeo.controller;

import com.cydeo.annotation.DefaultExceptionMessage;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "UserController", description = "User API")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @RolesAllowed({"Admin"})
    @Operation(summary = "Get Users")
    public ResponseEntity<ResponseWrapper> getAllUsers() {
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity
                .ok(new ResponseWrapper("Users are successfully retrieved", userDTOList, HttpStatus.OK));
    }

    @RolesAllowed({"Admin"})
    @GetMapping("/{userName}")
    @Operation(summary = "Get Users by user name")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable(("userName")) String userName) {
        UserDTO userDTO = userService.findByUserName(userName);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully found", userDTO, HttpStatus.FOUND));
    }

    @PostMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "Create User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("User is successfully created", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "Update user")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully updated", userDTO, HttpStatus.FOUND));

    }

    @DeleteMapping("/{userName}")
    @RolesAllowed({"Admin"})
    @Operation(summary = "Delete user")
    @DefaultExceptionMessage(defaultMessage = "Failed to delete user")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("userName") String userName) throws TicketingProjectException {
        userService.delete(userName);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully deleted", HttpStatus.OK));

//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .body(new ResponseWrapper("User is successfully deleted",HttpStatus.OK));

        //204 - HttpStatus.NO_CONTENT
    }


}
