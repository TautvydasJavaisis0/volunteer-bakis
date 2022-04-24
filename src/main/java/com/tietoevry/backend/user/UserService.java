package com.tietoevry.backend.user;

import com.tietoevry.backend.initiative.InitiativeRepository;;
import com.tietoevry.backend.initiative.application.ApplicationService;
import com.tietoevry.backend.initiative.features.Feature;
import com.tietoevry.backend.initiative.features.FeatureRepository;
import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.session.SessionService;
import com.tietoevry.backend.user.model.Role;
import com.tietoevry.backend.user.model.User;
import com.tietoevry.backend.user.model.dto.UserDto;
import com.tietoevry.backend.user.model.dto.UserStatsDto;
import com.tietoevry.backend.util.UserStatsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SessionService sessionService;
    private final InitiativeRepository initiativeRepository;
    private  final FeatureRepository featureRepository;

    public List<UserDto> getUsers() {
        return userRepository
            .findAll()
            .stream()
            .map(UserMapper::toUserDto)
            .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserDto(long id) {
        return userRepository
            .findById(id)
            .map(UserMapper::toUserDto);
    }

    public User getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no such user with id " + id));
        return user.get();
    }

    public User getLoggedInUser() {
        if(sessionService.getSession().getUser() == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not logged in ");

        Optional<User> user = userRepository.findByEmail(sessionService.getSession().getUser().getEmail());

        return user.get();
    }

    public Optional<UserStatsDto> getUserStats(long id) {
        User user = userRepository.findById(id).get();
        List<Initiative> attendedInitiatives = initiativeRepository.getAttendedInitiatives(user.getId(), new Date(), true);
        int totalDays = UserStatsUtil.getTotalDays(initiativeRepository.getAppliedNotActiveInitiatives(user.getId(), new Date()));
        int totalRated = Math.toIntExact(user.getRatingCount());
        int totalAttended = attendedInitiatives.size();
        int totalApplied = Math.toIntExact(user.getTotalApplications());
        return Optional.of(UserMapper.toUserStatsDto(totalDays, totalRated, totalAttended, totalApplied));
    }

    public HashMap<String, Integer> getUserFeatureStats(long id) {
        User user = userRepository.findById(id).get();
        List<Initiative> attendedInitiatives = initiativeRepository.getAppliedNotActiveInitiatives(user.getId(), new Date());
        List<Feature> featureList = featureRepository.findAll();

        return UserStatsUtil.countFeatures(attendedInitiatives, featureList);
    }

    public List<UserDto> getCandidates(long id) {
        return userRepository.getCandidatesFromInitiative(id)
            .stream()
            .map(UserMapper::toUserDto)
            .collect(Collectors.toList());
    }

    public UserDto createUser(UserDto user) {
        return saveUser(user);
    }

    public UserDto updateUser(UserDto user) {
        return saveUser(user);
    }

    private UserDto saveUser(UserDto user) {
        Optional<List<Role>> roleList = roleRepository.findAllByName("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = UserMapper.toUser(user, roleList);
        savedUser.setRatingCount(0L);
        savedUser.setTotalApplications(0L);
        userRepository.save(savedUser);
        return UserMapper.toUserDto(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
