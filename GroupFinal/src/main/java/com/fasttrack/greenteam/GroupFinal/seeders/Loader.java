package com.fasttrack.greenteam.GroupFinal.seeders;

import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
//import org.springframework.security.core.userdetails.User;

@Component
@RequiredArgsConstructor
public class Loader implements ApplicationRunner {
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User admin = new User();
        admin.setAdmin(Boolean.TRUE);
        Credentials credentials = new Credentials();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        admin.setCredentials(credentials);

        Profile profile = new Profile();
        profile.setFirst("System");
        profile.setLast("Administrator");
        admin.setProfile(profile);

        userRepository.save(admin);
        System.out.println("Admin user created with username 'admin' and password 'admin'");


        User user = new User();
        user.setAdmin(Boolean.FALSE);
        credentials = new Credentials();
        credentials.setUsername("user");
        credentials.setPassword("user");
        user.setCredentials(credentials);

        profile = new Profile();
        profile.setFirst("User");
        profile.setLast("User");
        user.setProfile(profile);

        userRepository.save(user);
        System.out.println("User user created with username 'user' and password 'user'");
    }
}
