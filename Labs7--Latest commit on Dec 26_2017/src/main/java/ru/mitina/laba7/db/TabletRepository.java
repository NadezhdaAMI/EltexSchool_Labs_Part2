package ru.mitina.laba7.db;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mitina.laba7.items.Tablet;

public interface TabletRepository extends JpaRepository<Tablet, Long> {

}