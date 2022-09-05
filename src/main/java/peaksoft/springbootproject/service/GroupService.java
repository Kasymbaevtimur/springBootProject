package peaksoft.springbootproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.springbootproject.dto.GroupRequest;
import peaksoft.springbootproject.dto.GroupResponse;
import peaksoft.springbootproject.entity.Course;
import peaksoft.springbootproject.entity.Group;
import peaksoft.springbootproject.repository.CourseRepository;
import peaksoft.springbootproject.repository.GroupRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;


    public GroupResponse create(GroupRequest request) {
        Group group = mapToEntity(request);
        Course course = courseRepository.findById(request.getCourseId()).get();
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        group.setCourses(courses);
        groupRepository.save(group);
        return groupResponse(group);
    }

    public GroupResponse update(long id, GroupRequest request) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            System.out.println("Group with id not found");
        }
        mapToUpdate(group.get(), request);
        return groupResponse(groupRepository.save(group.get()));
    }

    public GroupResponse getById(Long id) {
        Group group = groupRepository.findById(id).get();
        return groupResponse(group);
    }

    public void deleteById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Not found")
        );
        groupRepository.deleteById(group.getId());
    }

    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupResponse> responses = new ArrayList<>();
        for (Group group : groups) {
            responses.add(groupResponse(group));
        }

        return responses;
    }

    public void mapToUpdate(Group group, GroupRequest request) {
        group.setGroupName(request.getGroupName());
        group.setDateOfStart(request.getDateOfStart());
        group.setDateOfFinish(request.getDateOfFinish());
    }

    public Group mapToEntity(GroupRequest request) {
        Group group = new Group();
        group.setGroupName(request.getGroupName());
        group.setDateOfStart(request.getDateOfStart());
        group.setDateOfFinish(request.getDateOfFinish());
        group.setCreated(LocalDate.now());
        group.setIsActive(group.getIsActive());
        group.setDelete(group.getDelete());
        return group;
    }

    public GroupResponse groupResponse(Group group) {
        return GroupResponse.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .dateOfStart(group.getDateOfStart())
                .dateOfFinish(group.getDateOfFinish())
                .isActive(group.getIsActive())
                .courses(group.getCourses())
                .created(group.getCreated()).build();
    }


}
