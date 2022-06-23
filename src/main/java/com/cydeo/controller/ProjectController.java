package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping()
    @RolesAllowed({"Admin","Manager"})
    public ResponseEntity<ResponseWrapper> getProjects() {
        List<ProjectDTO> projectDTOList = projectService.listAllProjects();
        return ResponseEntity
                .ok(new ResponseWrapper("All projects are retrieved", projectDTOList, HttpStatus.OK));

    }

    @GetMapping("/{projectCode}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("projectCode") String projectCode) {
        ProjectDTO projectDTO = projectService.getByProjectCode(projectCode);
        return ResponseEntity
                .ok(new ResponseWrapper("All projects are retrieved", projectDTO, HttpStatus.OK));

    }

    @PostMapping()
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO) {

        projectService.save(projectDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper("Project is created", HttpStatus.OK));

    }


    @PutMapping()
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO) {
        projectService.update(projectDTO);

        return ResponseEntity
                .ok(new ResponseWrapper("Project is successfully updated", projectDTO, HttpStatus.FOUND));
    }


    @DeleteMapping("/{projectCode}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String projectcode) {
        projectService.delete(projectcode);
        return ResponseEntity
                .ok(new ResponseWrapper("Project is successfully deleted", HttpStatus.OK));
    }


    @GetMapping("/manager/project-status")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getProjectByManager() {

        List<ProjectDTO> projects = projectService.listAllProjectDetails();
        return ResponseEntity
                .ok(new ResponseWrapper("All projects are retrieved", projects, HttpStatus.OK));


    }

    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode) {
         projectService.complete(projectCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Project is successfully completed",HttpStatus.OK));
    }

}

