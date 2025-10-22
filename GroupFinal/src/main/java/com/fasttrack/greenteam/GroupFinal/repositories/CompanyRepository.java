package com.fasttrack.greenteam.GroupFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
