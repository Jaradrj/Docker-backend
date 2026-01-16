package com.example.demo.core.security.permissionevaluators;

import com.example.demo.domain.customlist.ListEntry;
import com.example.demo.domain.customlist.ListEntryService;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@Log4j2

public class UserPermissionEvaluator {
    private final ListEntryService listEntryService;

    public UserPermissionEvaluator(ListEntryService listEntryService) {
        this.listEntryService = listEntryService;
    }

    public boolean exampleEvaluator(User principal, UUID id) {
    //your code here
    return true;
  }

  public boolean isOwnEntryEvaluator (User principal, UUID id) {
        try {
            return Objects.equals(principal.getId(), listEntryService.getEntryById(id).getUser().getId());
        } catch (Exception e){
            return false;
        }
  }

  public boolean isThemselfEvaluator (User principal, UUID id) {
      return Objects.equals(principal.getId() ,id);
  }

}
