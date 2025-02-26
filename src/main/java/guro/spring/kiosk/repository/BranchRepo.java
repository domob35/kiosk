package guro.spring.kiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Branch;

public interface BranchRepo extends JpaRepository<Branch, Long> {
    
}
