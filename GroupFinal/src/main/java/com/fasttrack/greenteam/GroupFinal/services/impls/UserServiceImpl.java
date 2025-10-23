package com.fasttrack.greenteam.GroupFinal.services.impls;

import com.fasttrack.greenteam.GroupFinal.dtos.CredentialsDto;
import com.fasttrack.greenteam.GroupFinal.dtos.ProfileDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import com.fasttrack.greenteam.GroupFinal.exceptions.BadRequestException;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotFoundException;
import com.fasttrack.greenteam.GroupFinal.mappers.ProfileMapper;
import com.fasttrack.greenteam.GroupFinal.mappers.UserMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.CompanyRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;
    private final CompanyRepository companyRepository;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(this.userMapper::entityToDto)
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return this.userRepository.findById(id)
                .filter(User::getActive)
                .map(this.userMapper::entityToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        CredentialsDto credentials = userRequestDto.getCredentials();
        ProfileDto profile = userRequestDto.getProfile();
        if (!validateCredentials(credentials) || !validateProfile(profile)) {
            throw new BadRequestException("Invalid user data");
        }
//        if (userRepository.existsByCredentialsUsernameAndActiveIsFalse(credentials.getUsername())) {
//            User user = userRepository.findByCredentialsUsernameAndCredentialsPassword(credentials.getUsername(),
//                    credentials.getPassword());
//            user.setActive(Boolean.valueOf(true));
//            user.setProfile(profileMapper.dtoToEntity(userRequestDto.getProfile()));
//            return userMapper.entityToDto(userRepository.save(user));
//        }
        if (userRepository.existsByCredentialsUsername(credentials.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        User user = this.userMapper.dtoToEntity(userRequestDto);

        System.out.println("Creating user: " + user);
        System.out.println("User Admin: " + user.getAdmin());
        user.setAdmin(userRequestDto.getIsAdmin());
        user.setActive(Boolean.FALSE);
        user.setStatus("PENDING");
        Optional<Company> company = companyRepository.findById(userRequestDto.getCompanyId());
        company.ifPresent(value -> user.getCompanies().add(value));
        User savedUser = this.userRepository.save(user);

        return this.userMapper.entityToDto(savedUser);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User previousUser = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Update user fields here
        User newUser = this.userMapper.dtoToEntity(userRequestDto);
        for (java.lang.reflect.Field field : User.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object newValue = field.get(newUser);
                if (newValue != null) {
                    field.set(previousUser, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to update user", e);
            }
        }
        return userMapper.entityToDto(userRepository.save(previousUser));
    }

    @Override
    public UserResponseDto deleteUser(Long id) {
        if (!userRepository.existsById(id) || userRepository.existsByIdAndActiveIsFalse(id)) {
            throw new NotFoundException("User not found");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setActive(Boolean.valueOf(false));
        userRepository.save(user);
        return userMapper.entityToDto(user);
    }

    @Override
    public List<UserResponseDto> getActiveUsers() {
        return userRepository.findAllByActiveIsTrue()
                .stream()
                .map(userMapper::entityToDto)
                .toList();
    }

    @Override
    public List<UserResponseDto> getAdminUsers() {
        return userRepository.findAllByAdminIsTrue()
                .stream()
                .map(userMapper::entityToDto)
                .toList();
    }

    @Override
    public boolean isAdmin(Long userId) {
        return userRepository.existsByIdAndActiveIsTrueAndAdminIsTrue(userId);
    }

    public Boolean validateCredentials(CredentialsDto credentialsDto) {
        if (credentialsDto == null) {
            throw new BadRequestException("Credentials information is required");
        }
        if (credentialsDto.getUsername() == null || credentialsDto.getUsername().isBlank()) {
            throw new BadRequestException("Username is required");
        }
        if (credentialsDto.getPassword() == null || credentialsDto.getPassword().isBlank()) {
            throw new BadRequestException("Password is required");
        }
        return (Boolean) true;
    }
    public Boolean validateProfile(ProfileDto profileDto) {
        if (profileDto == null) {
            throw new BadRequestException("Profile information is required");
        }
        if (profileDto.getFirstName() == null || profileDto.getFirstName().isBlank()) {
            throw new BadRequestException("First name is required");
        }
        if (profileDto.getLastName() == null || profileDto.getLastName().isBlank()) {
            throw new BadRequestException("Last name is required");
        }
        if (profileDto.getEmail() == null || profileDto.getEmail().isBlank()) {
            throw new BadRequestException("Email is required");
        }

        return (Boolean) true;
    }

    public User validateCredentials(String username, String password) {
        User user = userRepository.findByCredentialsUsernameAndCredentialsPassword(username, password);
        if (user == null) {
            throw new NotFoundException("Invalid credentials");
        }
        return user;
    }
}
