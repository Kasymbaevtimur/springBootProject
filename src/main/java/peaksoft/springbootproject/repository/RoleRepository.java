package peaksoft.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.springbootproject.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.roleName = :username")
    Role findByName(String username);
}