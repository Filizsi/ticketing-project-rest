package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getAllTasks() {
        List<TaskDTO> taskList = taskService.listAllTasks();
        return ResponseEntity
                .ok(new ResponseWrapper("Tasks are retrieved", taskList, HttpStatus.OK));
    }

    @GetMapping("/{taskId}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long id) {
        TaskDTO taskList = taskService.findById(id);
        return ResponseEntity
                .ok(new ResponseWrapper("Task is retrieved", taskList, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.save(taskDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Task is successfully created", HttpStatus.CREATED));
    }

    @DeleteMapping("/{taskId}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId") Long id) {
        taskService.delete(id);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully deleted", HttpStatus.OK));

    }

    @PutMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO) {
        taskService.update(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully updated", HttpStatus.FOUND));
    }


    @GetMapping("/employee/pending-tasks")
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper> employeePendingTasks() { //this is for specific employee
      List<TaskDTO> taskDTOList=taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity
                .ok(new ResponseWrapper("Tasks are retrieved", taskDTOList, HttpStatus.OK));
    }

    @PutMapping("/employee/update")//this is for specific employee
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO taskDTO) {
        taskService.updateStatus(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully updated", HttpStatus.FOUND));

    }

    @GetMapping("/employee/archive")//show all the completed tasks belongs to this employee
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks() {
       List<TaskDTO> tasks = taskService.listAllTasksByStatus(Status.COMPLETE);//this is for specific employee
        return ResponseEntity
                .ok(new ResponseWrapper("Tasks are retrieved", tasks, HttpStatus.OK));
    }

}
