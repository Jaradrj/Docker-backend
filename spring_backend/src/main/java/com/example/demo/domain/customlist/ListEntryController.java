package com.example.demo.domain.customlist;


import com.example.demo.domain.customlist.dto.ListEntryDTO;
import com.example.demo.domain.customlist.dto.ListEntryMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/list-entries")
public class ListEntryController {

    private final ListEntryService entryService;
    private final ListEntryMapper entryMapper;

    @Autowired
    public ListEntryController(ListEntryService entryService, ListEntryMapper entryMapper) {
        this.entryService = entryService;
        this.entryMapper = entryMapper;
    }

    @GetMapping
    public ResponseEntity<List<ListEntryDTO>> getAllEntries() {
        List<ListEntry> entries = entryService.findAll();
        return new ResponseEntity<>(entryMapper.toDTOs(entries), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListEntryDTO> getEntryById(@PathVariable UUID id) {
        try {
            ListEntry entry = entryService.findById(id);
            return new ResponseEntity<>(entryMapper.toDTO(entry), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ListEntryDTO>> getEntriesByUser(@PathVariable UUID id) {
        List<ListEntry> entries = entryService.getEntriesByUser(id);
        return new ResponseEntity<>(entryMapper.toDTOs(entries), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListEntryDTO> updateEntry(@PathVariable UUID id, @RequestBody @Valid ListEntryDTO entryDTO) {
        try {
            ListEntry entryToUpdate = entryMapper.fromDTO(entryDTO);
            entryToUpdate.setId(id);
            ListEntry updated = entryService.updateEntry(entryToUpdate);
            return ResponseEntity.ok(entryMapper.toDTO(updated));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ListEntryDTO> createEntry(@RequestBody @Valid ListEntryDTO entryDTO) {
        ListEntry saved = entryService.saveEntry(entryMapper.fromDTO(entryDTO));
        return new ResponseEntity<>(entryMapper.toDTO(saved), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable UUID id) {
        entryService.deleteEntryById(id);
        return ResponseEntity.noContent().build();
    }
}
