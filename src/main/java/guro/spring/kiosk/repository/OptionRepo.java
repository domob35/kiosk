package guro.spring.kiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guro.spring.kiosk.entity.Option;

public interface OptionRepo extends JpaRepository<Option, Long> {
    
}
