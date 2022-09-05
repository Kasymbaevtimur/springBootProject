package peaksoft.springbootproject.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootproject.dto.GroupRequest;
import peaksoft.springbootproject.dto.GroupResponse;
import peaksoft.springbootproject.dto.StudentResponse;
import peaksoft.springbootproject.service.GroupService;
import peaksoft.springbootproject.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/groups")
@PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
@Tag(name = "Group Api", description = "User with role admin, teacher can add, update,get all groups, but only admin can delete")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "create group ", description = "we can create group")
    public GroupResponse create(@RequestBody GroupRequest request) {
        return groupService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update group ", description = "we can update group")
    public GroupResponse update(@PathVariable long id, @RequestBody GroupRequest request) {
        return groupService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "delete group by id ", description = "we can delete group")
    public void deleteById(@PathVariable long id) {
        groupService.deleteById(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get group by id ", description = "we can get group by id")
    public GroupResponse getById(@PathVariable long id) {
        return groupService.getById(id);
    }

    @GetMapping
    @Operation(summary = "get all groups ", description = "we can get all groups")
    public List<GroupResponse> getAllGroup() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}/students")
    @Operation(summary = " get student in this group ", description = "we can student in this group")
    public List<StudentResponse> getStudents(@PathVariable Long id) {
        return userService.getStudentByGroup(id);
    }
}
