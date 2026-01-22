package com.example.demo.domain.customlist;

import com.example.demo.core.generic.AbstractRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ListEntryRepository extends AbstractRepository<ListEntry> {
    List<ListEntry> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

    List<ListEntry> findAllByUserIdAndImportanceOrderByCreatedAtDesc(UUID userId, ListEntry.Importance importance);

    List<ListEntry> findAllByUserId(UUID userId, Sort sort);

    List<ListEntry> findAllByUserIdAndImportance(UUID userId, ListEntry.Importance importance, Sort sort);

    default List<ListEntry> findAllByUserId(UUID userId, String importance, Sort sort) {
        try {
            if (importance == null || importance.isBlank()) {
                return sort != null ? findAllByUserId(userId, sort) : findAllByUserIdOrderByCreatedAtDesc(userId);
            }
            ListEntry.Importance importanceEnum = ListEntry.Importance.valueOf(importance.toUpperCase());
            return sort != null ? findAllByUserIdAndImportance(userId, importanceEnum, sort)
                    : findAllByUserIdAndImportanceOrderByCreatedAtDesc(userId, importanceEnum);
        } catch (IllegalArgumentException e) {
            return findAllByUserIdOrderByCreatedAtDesc(userId);
        }
    }
    @Query("select l from ListEntry l where l.user.id = ?1")
    List<ListEntry> findAllByUserId(UUID userId, PageRequest pageRequest);

    @Query("select count(distinct l) from ListEntry l")
    long getListEntryCount();

    @Query("select count(distinct l) from ListEntry l where upper(l.user.email) like upper(?1)")
    long countDistinctByUser_EmailLikeIgnoreCase(String email);

}