package peaksoft.springbootproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootproject.dto.TeacherRequest;
import peaksoft.springbootproject.dto.TeacherResponse;
import peaksoft.springbootproject.dto.TeacherResponseView;
import peaksoft.springbootproject.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/teachers")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@Tag(name = "Teacher Api", description = "User with role only admin can add, update,delete or get all teachers")
public class TeacherController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "create teacher", description = "we can create teacher")
    public TeacherResponse create(@RequestBody TeacherRequest request) {
        return userService.createTeacher(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update teacher", description = "we can update teacher")
    public TeacherResponse update(@PathVariable long id, @RequestBody TeacherRequest request) {
        return userService.updateTeacher(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete teacher by id", description = "we can delete teacher")
    public void deleteById(@PathVariable long id) {
        userService.deleteTeacherById(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get teacher by id", description = "we can get teacher by id")
    public TeacherResponse getById(@PathVariable long id) {
        return userService.getTeacherById(id);
    }

    @GetMapping("/all")
    @Operation(summary = "get all teachers", description = "we can get all teachers")
    public List<TeacherResponse> getAllTeachers() {
        return userService.getAllTeachers();
    }

    @GetMapping
    public TeacherResponseView getAll(@RequestParam(name = "text", required = false) String text,
                                      @RequestParam int page,
                                      @RequestParam int size,
                                      @RequestParam(name = "fromDate", required = false) String fromDate,
                                      @RequestParam(name = "endDate", required = false) String endDate) {
        if (fromDate == null && endDate == null) {
            return userService.getAllTeacherPagination(text, page, size, null, null);
        } else {
            return userService.getAllTeacherPagination(text, page, size, LocalDate.parse(fromDate), LocalDate.parse(endDate));
        }
    }
}


