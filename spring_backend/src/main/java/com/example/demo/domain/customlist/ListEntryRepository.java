package com.example.demo.domain.customlist;

import com.example.demo.core.generic.AbstractRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ListEntryRepository extends AbstractRepository<ListEntry> {
    // TODO: Add filtering options
    @Query("select l from ListEntry l where l.user.id = ?1")
    List<ListEntry> findAllByUserId(UUID userId, PageRequest pageRequest);

    @Query("select count(distinct l) from ListEntry l")
    long getListEntryCount();

    @Query("select count(distinct l) from ListEntry l where upper(l.user.email) like upper(?1)")
    long countDistinctByUser_EmailLikeIgnoreCase(String email);

}