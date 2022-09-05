package peaksoft.springbootproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.springbootproject.dto.*;
import peaksoft.springbootproject.entity.*;
import peaksoft.springbootproject.repository.CourseRepository;
import peaksoft.springbootproject.repository.GroupRepository;
import peaksoft.springbootproject.repository.RoleRepository;
import peaksoft.springbootproject.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public List<StudentResponse> getAllStudents() {
        List<User> users = userRepository.getAllByRoles("STUDENT");
        List<StudentResponse> responses = new ArrayList<>();
        for (User us : users) {
            StudentResponse resp = mapToStudentResponse(us);
            responses.add(resp);
        }
        return responses;
    }

    public List<User> search(String name, LocalDate fromDate, LocalDate endDate, Pageable pageable) {

        String text = name == null ? "" : name;
        List<User> userList = userRepository.searchAndPagination("STUDENT", text, pageable);
        List<User> users = new ArrayList<>();


        if (fromDate == null && endDate == null) {
            return userList;
        }  else {
            for (User us : userList) {
                if (!(us.getCreated().isBefore(fromDate) || us.getCreated().isAfter(endDate))) {
                    users.add(us);
                }
            }
            return users;
        }
    }

    public StudentResponseView getAllStudentPagination(String text, int page, int size, LocalDate fromDate, LocalDate endDate) {
        StudentResponseView responseView = new StudentResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        responseView.setResponses(view(search(text.toUpperCase(), fromDate, endDate, pageable)));
        return responseView;

    }

    public List<StudentResponse> view(List<User> users) {
        List<StudentResponse> responses = new ArrayList<>();
        for (User user1 : users) {
            responses.add(mapToStudentResponse(user1));
        }
        return responses;

    }

    public List<User> searchTeacher(String name, LocalDate fromDate, LocalDate endDate, Pageable pageable) {

        String text = name == null ? "" : name;
        List<User> userList = userRepository.searchAndPagination("TEACHER", text.toUpperCase(), pageable);
        List<User> users = new ArrayList<>();


        if (fromDate == null && endDate == null) {
            return userList;
        } else {
            for (User us : userList) {
                if (!(us.getCreated().isBefore(fromDate) || us.getCreated().isAfter(endDate))) {
                    users.add(us);

                }
            }

            return users;
        }
    }


    public TeacherResponseView getAllTeacherPagination(String text, int page, int size, LocalDate fromDate, LocalDate endDate) {
        TeacherResponseView responseView = new TeacherResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        responseView.setTeacherResponses(view2(searchTeacher(text.toUpperCase(), fromDate, endDate, pageable)));
        return responseView;

    }

    public List<TeacherResponse> view2(List<User> users) {
        List<TeacherResponse> responses = new ArrayList<>();
        for (User user1 : users) {
            responses.add(mapToTeacherResponse(user1));
        }
        return responses;

    }


    public List<TeacherResponse> getAllTeachers() {
        List<User> users = userRepository.getAllByRoles("TEACHER");
        List<TeacherResponse> responses = new ArrayList<>();
        for (User us : users) {
            TeacherResponse resp = mapToTeacherResponse(us);
            responses.add(resp);
        }
        return responses;
    }

    public StudentResponse register(StudentRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated(LocalDate.now());
        user.setStudyFormat(StudyFormat.valueOf(request.getStudyFormat()));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("STUDENT"));
        user.setRoles(roles);
        userRepository.save(user);
        return mapToStudentResponse(user);
    }

    public StudentResponse createStudent(StudentRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated(LocalDate.now());
        user.setStudyFormat(StudyFormat.valueOf(request.getStudyFormat()));
        Group group = groupRepository.findById(request.getGroupId()).get();
        user.setGroup(group);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("STUDENT"));
        user.setRoles(roles);
        userRepository.save(user);
        return mapToStudentResponse(user);
    }

    public TeacherResponse createTeacher(TeacherRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setCreated(LocalDate.now());
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("TEACHER"));
        Course course = courseRepository.findById(request.getCourseId()).get();
        user.setRoles(roles);
        user.setCourse(course);
        userRepository.save(user);
        return mapToTeacherResponse(user);
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            log.info("Student with email - %d,not found", id);
        }
        mapToUpdateStudent(user, request);
        return mapToStudentResponse(userRepository.save(user));
    }

    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            log.info("Teacher with email - %d,not found", id);
        }
        mapToUpdateTeacher(user, request);
        return mapToTeacherResponse(userRepository.save(user));
    }


    public void mapToUpdateStudent(User user, StudentRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAge(request.getAge());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStudyFormat(StudyFormat.valueOf(request.getStudyFormat()));
        user.setGroup(groupRepository.findById(request.getGroupId()).get());
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(request.getRoleName()));
        user.setRoles(roles);

    }

    public void mapToUpdateTeacher(User user, TeacherRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        Course course = courseRepository.findById(request.getCourseId()).get();
        user.setCourse(course);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(request.getRoleName()));

        user.setRoles(roles);


    }

    private StudentResponse mapToStudentResponse(User user) {
        return StudentResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .email(user.getEmail())
                .studyFormat(user.getStudyFormat())
                .created(user.getCreated())
                .isActive(user.getIsActive())
                .role(user.getRoles())
                .groups(user.getGroup())
                .build();
    }

    private TeacherResponse mapToTeacherResponse(User user) {
        return TeacherResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRoles())
                .isActive(user.getIsActive())
                .created(user.getCreated())
                .course(user.getCourse())
                .build();

    }

    public StudentResponse getStudentById(Long id) {
        User user = userRepository.findById(id).get();
        User user1 = null;
        for (int i = 0; i < user.getRoles().size(); i++) {
            if (user.getRoles().get(i).getRoleName().equals("STUDENT")) {
                user1 = user;
            } else if (user1 == null) {
                log.info("Student with email - %d,not found", id);

            }


        }
        return mapToStudentResponse(user1);

    }

    public TeacherResponse getTeacherById(Long id) {
        User user = userRepository.findById(id).get();
        User user1 = null;
        for (int i = 0; i < user.getRoles().size(); i++) {
            if (user.getRoles().get(i).getRoleName().equals("TEACHER")) {
                user1 = user;
            } else if (user1 == null) {
                log.info("Teacher. with email - %d,not found", id);

            }

        }
        return mapToTeacherResponse(user1);

    }

    public void deletedStudentById(Long id) {
        User user = userRepository.findById(id).get();
        User user1 = null;
        for (int i = 0; i < user.getRoles().size(); i++) {
            if (user.getRoles().get(i).getRoleName().equals("STUDENT")) {
                user1 = user;
            } else if (user1 == null) {
                log.info("Student. with email - %d,not found", id);
            }
        }
        userRepository.delete(user1);
    }

    public void deleteTeacherById(Long id) {
        User user = userRepository.findById(id).get();
        User user1 = null;
        for (int i = 0; i < user.getRoles().size(); i++) {
            if (user.getRoles().get(i).getRoleName().equals("TEACHER")) {
                user1 = user;
            } else if (user1 == null) {
                log.info("Teacher with email - %d,not found", id);
            }
        }
        userRepository.delete(user1);
    }


    public List<StudentResponse> getStudentByGroup(Long groupId) {
        List<StudentResponse> responses = new ArrayList<>();
        List<User> users = userRepository.getStudentsByGroupId(groupId);
        for (User us : users) {
            responses.add(mapToStudentResponse(us));
        }
        return responses;
    }

    public int getSizeStudentInCompany(Long companyId) {
        List<User> users = userRepository.getStudentByCompany(companyId);
        return users.size();
    }


}


































