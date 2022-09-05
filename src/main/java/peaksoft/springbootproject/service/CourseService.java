package peaksoft.springbootproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.springbootproject.dto.CourseRequest;
import peaksoft.springbootproject.dto.CourseResponse;
import peaksoft.springbootproject.entity.Company;
import peaksoft.springbootproject.entity.Course;
import peaksoft.springbootproject.repository.CompanyRepository;
import peaksoft.springbootproject.repository.CourseRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CourseService {
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;


    public CourseResponse created(CourseRequest request) {
        Course course = mapToEntity(request);
        Company company = companyRepository.findById(request.getCompanyId()).get();
        course.setCompany(company);
        courseRepository.save(course);
        return courseResponse(course);
    }

    public CourseResponse update(long id, CourseRequest request) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            System.out.println("Course with id not found");
        }
        Company company = companyRepository.findById(request.getCompanyId()).get();
        Course course1 = mapToUpdate(course.get(), request);
        course1.setCompany(company);
        return courseResponse(courseRepository.save(course1));
    }

    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id).get();
        return courseResponse(course);
    }

    public void deleteById(Long id) {
        Course course = courseRepository.findById(id).get();
        courseRepository.delete(course);
    }

    public Course mapToEntity(CourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setDurationMonth(request.getDurationMonth());
        course.setDelete(course.getDelete());
        course.setIsActive(course.getIsActive());
        course.setCreated(LocalDate.now());
        return course;
    }

    public Course mapToUpdate(Course course, CourseRequest request) {
        course.setCourseName(request.getCourseName());
        course.setDurationMonth(request.getDurationMonth());
        return course;

    }

    public CourseResponse courseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .durationMonth(course.getDurationMonth())
                .company(course.getCompany())
                .created(course.getCreated())
                .isActive(course.getIsActive())
                .build();


    }

    public List<CourseResponse> geAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseResponse> responses = new ArrayList<>();
        for (Course course : courses) {
            responses.add(courseResponse(course));
        }
        return responses;

    }
}
