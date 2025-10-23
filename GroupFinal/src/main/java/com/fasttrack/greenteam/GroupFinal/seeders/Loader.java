// java
package com.fasttrack.greenteam.GroupFinal.seeders;

import com.fasttrack.greenteam.GroupFinal.entities.*;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import com.fasttrack.greenteam.GroupFinal.repositories.*;
import com.fasttrack.greenteam.GroupFinal.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Loader implements ApplicationRunner {
    private final UserService userService;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final TeamRepository teamRepository;
    private final AnnouncementRepository announcementRepository;
    private final ProjectRepository projectRepository;

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
        announcement1.setContent("We are excited to have you on board.");
        announcement1.setAuthor(admin);

        Announcement announcement2 = new Announcement();
        announcement2.setTitle("Eco Innovations Launches New Product Line");
        announcement2.setContent("Check out our latest sustainable products.");
        announcement2.setAuthor(admin);

        List<Announcement> announcemntsList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Announcement announcement = new Announcement();
            announcement.setTitle("Announcement " + (i + 1));
            announcement.setContent("This is the Content for announcement " + (i + 1) + ".");
            announcement.setAuthor(admin);
            announcement.setDate(new Timestamp(new Date().getTime() - (long)(Math.random() * 1_000_000_000)));
            announcemntsList.add(announcement);
        }
        Announcement announcement3 = new Announcement();
        announcement3.setTitle("Eco Innovations Launches New Product Line");
        announcement3.setContent("Check out our latest sustainable products.");
        announcement3.setAuthor(admin);

        Announcement announcement4 = new Announcement();
        announcement4.setTitle("Eco Innovations Launches New Product Line");
        announcement4.setContent("Check out our latest sustainable products.");
        announcement4.setAuthor(admin);

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
        announcement1.setCompany(company1);
        announcement2.setCompany(company1);
        announcement3.setCompany(company1);
        announcement4.setCompany(company1);
        for (Announcement ann : announcemntsList) {
            ann.setCompany(company2);
        }
        // Persist companies, teams, announcements
        companyRepository.save(company1);
        companyRepository.save(company2);
        teamRepository.save(team1);
        teamRepository.save(team2);
        announcementRepository.save(announcement1);
        announcementRepository.save(announcement2);
        announcementRepository.save(announcement3);
        announcementRepository.save(announcement4);
        announcementRepository.saveAll(announcemntsList);

        // Ensure users reflect associations (save again)
        userRepository.save(admin);
        userRepository.save(user);

        System.out.println("Sample companies, teams and announcements created and associated.");
        Company company = new Company();
        company.setName("FedEx");
        company.setDescription("FedEx client company");
        companyRepository.save(company);
        System.out.println("🏢 Company created: FedEx");

        Team team = new Team();
        team.setName("Green Team");
        team.setDescription("Frontend developers at FedEx");
        team.setCompany(company);


        team.getUsers().add(admin);
        team.getUsers().add(user);
        admin.getTeams().add(team);
        user.getTeams().add(team);


        company.getUsers().add(admin);
        company.getUsers().add(user);
        admin.getCompanies().add(company);
        user.getCompanies().add(company);


        teamRepository.save(team);
        companyRepository.save(company);
        userRepository.saveAll(List.of(admin, user));

        Project project1 = new Project();
        project1.setName("Website Redesign");
        project1.setDescription("Rebuild the landing page using Angular and Spring Boot");
        project1.setActive(true);
        project1.setTeam(team);  //  Link to Green Team

        Project project2 = new Project();
        project2.setName("Internal Dashboard");
        project2.setDescription("Develop an analytics dashboard for FedEx");
        project2.setActive(true);
        project2.setTeam(team);

        projectRepository.saveAll(List.of(project1, project2));

        System.out.println(" Projects seeded and linked to Green Team!");

    }
}
