package com.example.myfinances.restControllers;

import com.example.myfinances.models.dto.CheckAuthDto;
import com.example.myfinances.models.dto.IdDto;
import com.example.myfinances.services.UserService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    final UserService userService;
    @GetMapping ("/checkAuth")
    public ResponseEntity<CheckAuthDto> whoAmI(Principal principal) {
        if(principal == null) {
            return ResponseEntity.ok(CheckAuthDto.builder().value(false).build());
        } else {
            return ResponseEntity.ok(CheckAuthDto.builder().value(true).build());
        }

//        System.out.println(principal);
//        return ResponseEntity.ok(IdDto.builder()
//                .id(userService.getLoggedUserId(principal)).build());
    }
}
