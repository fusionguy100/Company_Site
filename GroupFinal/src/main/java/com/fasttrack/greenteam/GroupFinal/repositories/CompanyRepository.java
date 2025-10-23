package com.fasttrack.greenteam.GroupFinal.repositories;

import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
