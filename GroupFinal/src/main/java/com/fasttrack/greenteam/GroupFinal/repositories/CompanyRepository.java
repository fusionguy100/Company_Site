package com.fasttrack.greenteam.GroupFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fasttrack.greenteam.GroupFinal.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
