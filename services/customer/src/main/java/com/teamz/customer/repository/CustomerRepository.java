package com.teamz.customer.repository;

import com.teamz.customer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<User,Long> {

}
