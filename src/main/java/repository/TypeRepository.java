package repository;

import entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type,Integer> {

    Type findByTypeName(String typeName);
}
