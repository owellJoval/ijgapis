package com.example.ijgapis.Controllers;

import com.example.ijgapis.Models.DepositRange;
import com.example.ijgapis.Repositories.DepositRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deposit-ranges")
@CrossOrigin(origins = "*")
public class DepositRangeController {

    @Autowired
    private DepositRangeRepository depositRangeRepository;

    @GetMapping
    public ResponseEntity<List<DepositRange>> getAllDepositRanges() {
        return ResponseEntity.ok(depositRangeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepositRange> getDepositRangeById(@PathVariable Long id) {
        return depositRangeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DepositRange> createDepositRange(@RequestBody DepositRange depositRange) {
        return ResponseEntity.ok(depositRangeRepository.save(depositRange));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepositRange> updateDepositRange(@PathVariable Long id, @RequestBody DepositRange depositRange) {
        return depositRangeRepository.findById(id)
                .map(existingRange -> {
                    depositRange.setId(id);
                    return ResponseEntity.ok(depositRangeRepository.save(depositRange));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepositRange(@PathVariable Long id) {
        return depositRangeRepository.findById(id)
                .map(range -> {
                    depositRangeRepository.delete(range);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 