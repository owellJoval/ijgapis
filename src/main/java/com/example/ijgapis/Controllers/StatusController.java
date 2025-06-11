package com.example.ijgapis.Controllers;

import com.example.ijgapis.Models.Status;
import com.example.ijgapis.Repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
@CrossOrigin(origins = "*")
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        return ResponseEntity.ok(statusRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable Long id) {
        return statusRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        return ResponseEntity.ok(statusRepository.save(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable Long id, @RequestBody Status status) {
        return statusRepository.findById(id)
                .map(existingStatus -> {
                    status.setId(id);
                    return ResponseEntity.ok(statusRepository.save(status));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        return statusRepository.findById(id)
                .map(status -> {
                    statusRepository.delete(status);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 