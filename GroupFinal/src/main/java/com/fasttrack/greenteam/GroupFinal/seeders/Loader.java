package com.fasttrack.greenteam.GroupFinal.seeders;

import com.fasttrack.greenteam.GroupFinal.entities.*;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import com.fasttrack.greenteam.GroupFinal.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class Loader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        // ====== CREATE ADMIN USER ======
        User admin = new User();
        admin.setAdmin(true);
        Credentials adminCreds = new Credentials();
        adminCreds.setUsername("admin");
        adminCreds.setPassword("admin");
        admin.setCredentials(adminCreds);

        Profile adminProfile = new Profile();
        adminProfile.setFirst("System");
        adminProfile.setLast("Administrator");
        adminProfile.setEmail("admin@example.com");
        adminProfile.setPhone(randomUSPhoneNumber());
        admin.setProfile(adminProfile);

        userRepository.save(admin);
        System.out.println("✅ Admin user created.");

        // ====== CREATE USERS ======
        User alice = createUser("alice", "Alice", "Green");
        User bob = createUser("bob", "Bob", "Blue");
        User charlie = createUser("charlie", "Charlie", "Brown");
        userRepository.saveAll(List.of(alice, bob, charlie));
        System.out.println("✅ Test users created.");

        // ====== CREATE COMPANIES ======
        Company company1 = new Company();
        company1.setName("GreenTech Solutions");
        company1.setDescription("Renewable energy innovators");

        Company company2 = new Company();
        company2.setName("Eco Innovations");
        company2.setDescription("Sustainable product designers");

        Company company3 = new Company();
        company3.setName("FedEx Logistics");
        company3.setDescription("Shipping and logistics technology");

        companyRepository.saveAll(List.of(company1, company2, company3));
        System.out.println("🏢 Companies created.");

        // ====== CREATE TEAMS ======
        Team greenDev = new Team();
        greenDev.setName("Green Dev Team");
        greenDev.setDescription("Developers at GreenTech Solutions");
        greenDev.setCompany(company1);

        Team ecoMarketing = new Team();
        ecoMarketing.setName("Eco Marketing Team");
        ecoMarketing.setDescription("Marketing at Eco Innovations");
        ecoMarketing.setCompany(company2);

        Team fedexFrontend = new Team();
        fedexFrontend.setName("FedEx Frontend Team");
        fedexFrontend.setDescription("Frontend developers at FedEx");
        fedexFrontend.setCompany(company3);

        Team adminOnly = new Team();
        adminOnly.setName("Admin Only Team");
        adminOnly.setDescription("A Team for admin users only");
        adminOnly.setCompany(company1);

        teamRepository.saveAll(List.of(greenDev, ecoMarketing, fedexFrontend, adminOnly));
        System.out.println("👥 Teams created.");

        // ====== LINK USERS TO COMPANIES & TEAMS ======
        linkUserToCompanyAndTeam(admin, company1, greenDev);
        linkUserToCompanyAndTeam(alice, company1, greenDev);
        linkUserToCompanyAndTeam(admin, company1, adminOnly);

        linkUserToCompanyAndTeam(admin, company2, ecoMarketing);
        linkUserToCompanyAndTeam(bob, company2, ecoMarketing);

        linkUserToCompanyAndTeam(admin, company3, fedexFrontend);
        linkUserToCompanyAndTeam(charlie, company3, fedexFrontend);

        userRepository.saveAll(List.of(admin, alice, bob, charlie));
        companyRepository.saveAll(List.of(company1, company2, company3));
        teamRepository.saveAll(List.of(greenDev, ecoMarketing, fedexFrontend, adminOnly));

        System.out.println("🔗 Users linked to companies and teams.");

        // ====== CREATE PROJECTS ======
        Project p1 = createProject("Solar Panel Optimizer", "Smart energy analytics", greenDev);
        Project p2 = createProject("Eco Branding Site", "Marketing microsite", ecoMarketing);
        Project p3 = createProject("Shipment Dashboard", "Internal logistics monitoring", fedexFrontend);

        projectRepository.saveAll(List.of(p1, p2, p3));
        System.out.println("🧱 Projects created and linked to teams.");

        // ====== CREATE ANNOUNCEMENTS ======
        Announcement a1 = createAnnouncement("Welcome to GreenTech!", "We are excited to build a greener future.", admin, company1);
        Announcement a2 = createAnnouncement("Eco Innovations Launch", "New eco-friendly products are here!", admin, company2);
        Announcement a3 = createAnnouncement("FedEx Innovation Week", "Showcasing our latest logistics tech.", admin, company3);

        announcementRepository.saveAll(List.of(a1, a2, a3));
        System.out.println("📢 Announcements created for each company.");

        System.out.println("✅ Seeder completed successfully!");
    }

    // ====== HELPER METHODS ======

    private User createUser(String username, String first, String last) {
        User user = new User();
        user.setAdmin(false);

        Credentials creds = new Credentials();
        creds.setUsername(username);
        creds.setPassword(username);
        user.setCredentials(creds);

        Profile profile = new Profile();
        profile.setFirst(first);
        profile.setLast(last);
        profile.setEmail(username + "@example.com");
        profile.setPhone(randomUSPhoneNumber());
        user.setProfile(profile);

        return user;
    }

    private static String randomUSPhoneNumber() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int area = rnd.nextInt(200, 1000);      // 200-999 (first digit 2-9)
        int central = rnd.nextInt(200, 1000);   // 200-999 (NXX rule)
        int subscriber = rnd.nextInt(0, 10_000);
        return String.format("%03d-%03d-%04d", area, central, subscriber);
    }

    private void linkUserToCompanyAndTeam(User user, Company company, Team team) {
        company.getUsers().add(user);
        user.getCompanies().add(company);
        team.getUsers().add(user);
        user.getTeams().add(team);
    }

    private Project createProject(String name, String desc, Team team) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(desc);
        project.setActive(true);
        project.setTeam(team);
        return project;
    }

    private Announcement createAnnouncement(String title, String content, User author, Company company) {
        Announcement a = new Announcement();
        a.setTitle(title);
        a.setContent(content);
        a.setAuthor(author);
        a.setCompany(company);// 1 day ago
        Instant now = Instant.now();
        Instant start = now.minus(7, ChronoUnit.DAYS);
        long startMillis = start.toEpochMilli();
        long nowMillis = now.toEpochMilli();
        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, nowMillis);
        a.setDate(new Timestamp(randomMillis));

        return a;
    }
}
