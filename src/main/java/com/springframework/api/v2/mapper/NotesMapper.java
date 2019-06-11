package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.NotesDTO;
import com.springframework.models.Notes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface NotesMapper {
    NotesMapper INSTANCE = Mappers.getMapper(NotesMapper.class);

    NotesDTO notesToNotesDTO(Notes notes);
    Notes notesTdoToNotes(NotesDTO notesDTO);
}
