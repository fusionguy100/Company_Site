// java
package com.fasttrack.greenteam.GroupFinal.seeders;

import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import com.fasttrack.greenteam.GroupFinal.repositories.AnnouncementRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.CompanyRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.TeamRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Loader implements ApplicationRunner {
    private final UserService userService;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final TeamRepository teamRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    @Transactional
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

        Company company1 = new Company();
        company1.setName("GreenTech Solutions");
        company1.setIndustry("Renewable Energy");
        Company company2 = new Company();
        company2.setName("Eco Innovations");
        company2.setIndustry("Sustainable Products");

        Announcement announcement1 = new Announcement();
        announcement1.setTitle("Welcome to GreenTech Solutions!");
        announcement1.setMessage("We are excited to have you on board.");
        announcement1.setAuthor(admin);

        Announcement announcement2 = new Announcement();
        announcement2.setTitle("Eco Innovations Launches New Product Line");
        announcement2.setMessage("Check out our latest sustainable products.");
        announcement2.setAuthor(admin);

        Team team1 = new Team();
        team1.setName("Development Team");
        Team team2 = new Team();
        team2.setName("Marketing Team");

        // Associate users <-> companies and teams
        company1.getUsers().add(admin);
        company2.getUsers().add(user);
        admin.getCompanies().add(company1);
        user.getCompanies().add(company2);
        team1.getUsers().add(admin);
        team2.getUsers().add(user);
        admin.getTeams().add(team1);
        user.getTeams().add(team2);

        // Persist companies, teams, announcements
        companyRepository.save(company1);
        companyRepository.save(company2);
        teamRepository.save(team1);
        teamRepository.save(team2);
        announcementRepository.save(announcement1);
        announcementRepository.save(announcement2);

        // Ensure users reflect associations (save again)
        userRepository.save(admin);
        userRepository.save(user);

        System.out.println("Sample companies, teams and announcements created and associated.");
    }
}
