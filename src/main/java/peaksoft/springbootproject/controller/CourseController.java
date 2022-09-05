package peaksoft.springbootproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootproject.dto.CourseRequest;
import peaksoft.springbootproject.dto.CourseResponse;
import peaksoft.springbootproject.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/courses")
@PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
@Tag(name = "Course Api", description = "User with role admin,teacher can add, update, but only admin can delete")
public class CourseController {
    private final CourseService courseService;


    @PostMapping()
    @Operation(summary = "create course", description = "we can create course")
    public CourseResponse create(@RequestBody CourseRequest request) {
        return courseService.created(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update course", description = "we can update course")
    public CourseResponse update(@PathVariable long id, @RequestBody CourseRequest request) {
        return courseService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "delete course by id", description = "we can delete course by id")
    public void deleteById(@PathVariable long id) {
        courseService.deleteById(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get course by id", description = "we can get course by id")
    public CourseResponse getById(@PathVariable long id) {
        return courseService.getById(id);
    }

    @GetMapping
    @Operation(summary = "get all courses", description = "we can get all course")
    public List<CourseResponse> getAllCourses() {
        return courseService.geAllCourses();
    }

}
