package com.teamz.customer.service;


import com.teamz.customer.entity.EmailAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailAdminRepository extends JpaRepository<EmailAdmin,Long> {
    List<EmailAdmin> findBySubjectOrderByIdDesc(String Subject);
}
