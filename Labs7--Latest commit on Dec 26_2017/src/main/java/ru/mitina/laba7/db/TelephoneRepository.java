package ru.mitina.laba7.db;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mitina.laba7.items.Telephone;

public interface TelephoneRepository extends JpaRepository<Telephone, Long> {

}
