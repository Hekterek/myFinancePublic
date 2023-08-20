package com.example.myfinances.repositories;

import com.example.myfinances.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    public List<Account> findAllByUserId(Integer userId);
    public void deleteById(Integer id);
}
