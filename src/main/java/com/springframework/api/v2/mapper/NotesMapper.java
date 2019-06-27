package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.NotesDTO;
import com.springframework.models.Notes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotesMapper {

  NotesDTO notesToNotesDTO(Notes notes);

  @Mapping(target = "recipe", ignore = true)
  Notes notesDtoToNotes(NotesDTO notesDTO);
}
