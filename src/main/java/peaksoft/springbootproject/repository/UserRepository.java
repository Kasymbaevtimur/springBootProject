package peaksoft.springbootproject.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.springbootproject.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select u from User u  join u.roles r where  r.roleName=:roleName and( upper(u.firstName) like concat('%',:text,'%')" +
            " or upper(u.lastName) like concat('%',:text,'%') or upper(u.email)like concat('%',:text,'%'))")
    List<User>searchAndPagination(@Param("roleName")String roleName, @Param("text")String text, Pageable pageable);

    @Query("select u from User u join  Group g on u.group.id=g.id where g.id=?1")
    List<User> getStudentsByGroupId(Long groupId);

    @Query("select u from User u join u.roles r where r.roleName=?1")
    List<User> getAllByRoles(String roleName);
    @Query("select u from User u join Group g on u.group.id = g.id join g.courses c join Company com on  c.company.id = com.id where com.id = ?1")
    List<User>getStudentByCompany(long companyId);


}
