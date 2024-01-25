package com.back.BuyAhead.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.back.BuyAhead.Domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //내림차순으로 정렬하여 조회
    List<User> findAllByOrderByIdDesc();

    Optional<User> findByEmail(String email);

}
