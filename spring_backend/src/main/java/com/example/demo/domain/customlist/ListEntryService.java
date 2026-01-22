package com.example.demo.domain.customlist;

import com.example.demo.core.exception.NoSuchListEntryException;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.demo.core.generic.AbstractServiceImpl;

import java.util.*;

@Service
public class ListEntryService extends AbstractServiceImpl<ListEntry> {

    private final  UserService userService;
    private final ListEntryRepository repository;

    public ListEntryService(UserService userService, ListEntryRepository repository) {
        super(repository);
        this.repository = repository;
        this.userService = userService;
    }

    public List<ListEntry> getAllEntries(Optional<Integer> page) {
        return repository.findAll(PageRequest.of(page.orElse(0), 10)).getContent();
    }

    public ListEntry getEntryById(UUID id) throws NoSuchListEntryException {
        return repository.findById(id).orElseThrow(() -> new NoSuchListEntryException(id));
    }

    public Integer getPages() {
        return Math.toIntExact(repository.getListEntryCount() / 10 + 1);
    }

    public Integer getPagesForUser(String email) {
        return Math.toIntExact(repository.countDistinctByUser_EmailLikeIgnoreCase(email) / 10 + 1);
    }

    public List<ListEntry> getEntriesByUser(String email, Optional<Integer> page) {
        UUID userId = userService.getUserByMail(email).getId();
        return repository.findAllByUserId(userId, PageRequest.of(page.orElse(0), 10));
    }

    @Transactional
    public ListEntry updateEntry(ListEntry oldEntry) throws NoSuchElementException {
        ListEntry updatedEntry = repository
                .findById(oldEntry.getId())
                .orElseThrow(() -> new NoSuchListEntryException(oldEntry.getId()));
        updatedEntry.setTitle(oldEntry.getTitle());
        updatedEntry.setText(oldEntry.getText());
        updatedEntry.setImportance(oldEntry.getImportance());
        repository.save(updatedEntry);
        return updatedEntry;
    }

    public void deleteEntryById(UUID id) {
        repository.deleteById(id);
    }

    public ListEntry saveEntry(ListEntry listEntry, String email) {
        User user = userService.getUserByMail(email);
        listEntry.setUser(user);
        return save(listEntry);
    }
}
