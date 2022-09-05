package peaksoft.springbootproject.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootproject.dto.StudentRequest;
import peaksoft.springbootproject.dto.StudentResponse;
import peaksoft.springbootproject.dto.StudentResponseView;
import peaksoft.springbootproject.service.UserService;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/students")
@PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
@Tag(name = "Student Api", description = "User with role admin, teacher can add, update,delete or get all students")
public class StudentController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "create student", description = "we can create student")
    public StudentResponse create(@RequestBody StudentRequest request) {
        return userService.createStudent(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update student", description = "we can update student")
    public StudentResponse update(@PathVariable long id, @RequestBody StudentRequest request) {
        return userService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete student", description = "we can delete student")
    public void deleteById(@PathVariable long id) {
        userService.deletedStudentById(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get student by id", description = "we can get student by id")
    public StudentResponse getById(@PathVariable long id) {
        return userService.getStudentById(id);

    }

    @GetMapping("/all")
    @Operation(summary = "get all students", description = "we can get all students")
    public List<StudentResponse> getAllStudents() {
        return userService.getAllStudents();
    }

    @GetMapping
    public StudentResponseView getAll(@RequestParam(name = "text", required = false) String text,
                                      @RequestParam  int page,
                                      @RequestParam int size,
                                      @RequestParam(name = "fromDate",required = false) String fromDate,
                                      @RequestParam(name = "endDate", required = false) String endDate ) {
        if (fromDate == null && endDate == null) {
            return userService.getAllStudentPagination(text, page, size, null, null);
        } else {
            return userService.getAllStudentPagination(text, page, size, LocalDate.parse(fromDate), LocalDate.parse(endDate));
        }
    }

}
