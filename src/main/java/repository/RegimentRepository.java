package repository;

import entity.Regiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegimentRepository extends JpaRepository<Regiment,Integer> {

    public Regiment findByCode(int code);
}
