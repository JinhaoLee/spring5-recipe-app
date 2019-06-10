package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.NotesDTO;
import com.springframework.models.Notes;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface NotesMapper {
    NotesDTO notesToNotesDTO(Notes notes);
}
