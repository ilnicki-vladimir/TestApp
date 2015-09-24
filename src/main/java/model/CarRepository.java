package model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Collection<Car> findByClientId(Long id);
    Optional<Car> findById(Long id);
}
