package ru.mitina.laba7.db;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mitina.laba7.items.Smartphone;

public interface SmartphoneRepository extends JpaRepository<Smartphone, Long> {

}