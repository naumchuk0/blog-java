package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.constants.Roles;
import org.example.dto.account.AuthResponseDTO;
import org.example.dto.account.LoginDTO;
import org.example.dto.account.UserCreateDTO;
import org.example.dto.account.UserItemDTO;
import org.example.entities.UserEntity;
import org.example.entities.UserRoleEntity;
import org.example.mapper.UserMapper;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.UserRoleRepository;
import org.example.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO dto) {
        try {
            var auth = service.login(dto);
            return ResponseEntity.ok(auth);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("signIn")
    public ResponseEntity<UserItemDTO> signIn(@RequestBody UserCreateDTO dto) {
        try {
            var user = UserEntity
                    .builder()
                    .email(dto.getEmail())
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .phone(dto.getPhone())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .build();
            var role = roleRepository.findByName(Roles.User);
            var userRole = UserRoleEntity.builder().role(role).user(user).build();
            userRepository.save(user);
            userRoleRepository.save(userRole);
            return new ResponseEntity<>(userMapper.uesrItemDTO(user), HttpStatus.CREATED);
        }catch(Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}