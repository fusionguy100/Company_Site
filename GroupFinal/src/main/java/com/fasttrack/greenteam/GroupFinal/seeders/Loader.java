package com.fasttrack.greenteam.GroupFinal.seeders;

import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.Project;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import com.fasttrack.greenteam.GroupFinal.repositories.CompanyRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.ProjectRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.TeamRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
//import org.springframework.security.core.userdetails.User;

@Component
@RequiredArgsConstructor
public class Loader implements ApplicationRunner {
    private final UserService userService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {


        User admin = new User();
        admin.setAdmin(true);
        admin.setActive(true);
        admin.setStatus("Active");

        Credentials adminCred = new Credentials();
        adminCred.setUsername("admin");
        adminCred.setPassword("admin");
        admin.setCredentials(adminCred);

        Profile adminProfile = new Profile();
        adminProfile.setFirst("System");
        adminProfile.setLast("Administrator");
        admin.setProfile(adminProfile);

        User user = new User();
        user.setAdmin(false);
        user.setActive(true);
        user.setStatus("Active");

        Credentials userCred = new Credentials();
        userCred.setUsername("user");
        userCred.setPassword("user");
        user.setCredentials(userCred);

        Profile userProfile = new Profile();
        userProfile.setFirst("User");
        userProfile.setLast("User");
        user.setProfile(userProfile);

        userRepository.saveAll(List.of(admin, user));
        System.out.println(" Users created: admin / user");


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
