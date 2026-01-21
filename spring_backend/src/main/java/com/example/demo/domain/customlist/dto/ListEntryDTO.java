package com.example.demo.domain.customlist.dto;

import com.example.demo.core.generic.AbstractDTO;
import com.example.demo.domain.customlist.ListEntry;
import com.example.demo.domain.user.dto.UserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class ListEntryDTO extends AbstractDTO {
    @NotBlank
    @Size(min = 3)
    private String title;
    @NotBlank
    @Size(max = 500)
    private String text;
    @NotNull
    private ListEntry.Importance importance;
    @NotNull
    private UserDTO user;
    private LocalDateTime createdAt;
}
