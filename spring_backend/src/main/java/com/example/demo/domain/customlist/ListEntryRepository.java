package com.example.demo.domain.customlist;

import com.example.demo.core.generic.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

@Repository
public interface ListEntryRepository extends AbstractRepository<ListEntry> {

    Page<ListEntry> findAllByUserIdAndImportance(
            UUID userId,
            ListEntry.Importance importance,
            Pageable pageable
    );

    Page<ListEntry> findAllByImportance(ListEntry.Importance importance, Pageable pageable);

    @Query("select l from ListEntry l where (:userId is null or l.user.id = :userId) and (:importance is null or l.importance = :importance) order by case l.importance when 'LOW' then 0 when 'MEDIUM' then 1 when 'HIGH' then 2 end asc, l.createdAt desc")
    Page<ListEntry> findAllOrderByImportanceAsc(@Param("userId") UUID userId, @Param("importance") ListEntry.Importance importance, Pageable pageable);

    @Query("select l from ListEntry l where (:userId is null or l.user.id = :userId) and (:importance is null or l.importance = :importance) order by case l.importance when 'LOW' then 0 when 'MEDIUM' then 1 when 'HIGH' then 2 end desc, l.createdAt desc")
    Page<ListEntry> findAllOrderByImportanceDesc(@Param("userId") UUID userId, @Param("importance") ListEntry.Importance importance, Pageable pageable);

    default Page<ListEntry> findAllByUserIdPageable(
            UUID userId,
            String importance,
            Pageable pageable
    ) {
        Pageable effectivePageable =
                pageable != null
                        ? pageable
                        : PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));

        try {
            if (importance == null || importance.isBlank()) {
                return findAllByUserId(userId, effectivePageable);
            }

            ListEntry.Importance importanceEnum =
                    ListEntry.Importance.valueOf(importance.trim().toUpperCase());

            return findAllByUserIdAndImportance(userId, importanceEnum, effectivePageable);
        } catch (IllegalArgumentException e) {
            return findAllByUserId(userId, effectivePageable);
        }
    }

    Page<ListEntry> findAllByUserId(UUID userId, Pageable effectivePageable);

    default Page<ListEntry> findAllPageable(
            String importance,
            Pageable pageable
    ) {
        Pageable effectivePageable =
                pageable != null
                        ? pageable
                        : PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));

        try {
            if (importance == null || importance.isBlank()) {
                return findAll(effectivePageable);
            }

            ListEntry.Importance importanceEnum =
                    ListEntry.Importance.valueOf(importance.trim().toUpperCase());

            return findAllByImportance(importanceEnum, effectivePageable);
        } catch (IllegalArgumentException e) {
            return findAll(effectivePageable);
        }
    }

    @Query("select count(distinct l) from ListEntry l")
    long getListEntryCount();

    @Query("select count(distinct l) from ListEntry l where upper(l.user.email) like upper(?1)")
    long countDistinctByUser_EmailLikeIgnoreCase(String email);
}
