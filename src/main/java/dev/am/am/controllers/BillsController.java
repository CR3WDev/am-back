package dev.am.am.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.am.am.dto.Bill;
import dev.am.am.services.BillService;

@RestController
@RequestMapping("/bills")
public class BillsController {
    
    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<Bill> save(@RequestBody Bill bill) {
        return ResponseEntity.ok().body(billService.save(bill));
    }

    @GetMapping()
    public ResponseEntity<List<Bill>> list() {
        List<Bill> bills = billService.findAll();
        return ResponseEntity.ok().body(bills);
    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getById(@PathVariable UUID id) {       			
            return ResponseEntity.ok().body(billService.findById(id));
    }
    
    @PutMapping()
    public ResponseEntity<Bill> update(@RequestBody Bill bill) {
    	return ResponseEntity.ok().body(billService.update(bill));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Bill> deleteById(@PathVariable UUID id) {
    	billService.deleteById(id);
    	return ResponseEntity.ok().build();
    }
    
}
