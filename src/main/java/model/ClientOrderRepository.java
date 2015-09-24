package model;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    Collection<ClientOrder> findByCarId(Long id);
}
