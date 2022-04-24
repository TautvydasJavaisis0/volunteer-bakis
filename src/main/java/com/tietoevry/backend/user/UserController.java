package com.tietoevry.backend.user;

import com.tietoevry.backend.user.model.dto.RateUserDto;
import com.tietoevry.backend.user.model.dto.UserDto;
import com.tietoevry.backend.user.model.dto.UserStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserDto(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found by id " + id));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/stats")
    public UserStatsDto getUserStats(@RequestParam Long id) {
        return userService.getUserStats(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not load user stats"));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/feature-stats")
    public HashMap<String, Integer> getUserFeatureStats(@RequestParam Long id) {
        return userService.getUserFeatureStats(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/by-initiative/{id}")
    public List<UserDto> getCandidates(@PathVariable Long id) {
        return userService.getCandidates(id);
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping
    public UserDto updateUser(@Valid @RequestBody UserDto user) {
        return userService.updateUser(user);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
