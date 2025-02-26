package guro.spring.kiosk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import guro.spring.kiosk.entity.Member;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Optional<Member> findByPhone(String phone);
}
