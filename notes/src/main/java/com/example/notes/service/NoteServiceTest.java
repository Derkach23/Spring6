package com.example.notes.service;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllNotes() {
        Note note1 = new Note(1L, "Title 1", "Content 1");
        Note note2 = new Note(2L, "Title 2", "Content 2");

        when(noteRepository.findAll()).thenReturn(List.of(note1, note2));

        List<Note> notes = noteService.findAllNotes();

        assertEquals(2, notes.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testFindNoteById() {
        Note note = new Note(1L, "Title 1", "Content 1");
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        Optional<Note> foundNote = noteService.findNoteById(1L);

        assertTrue(foundNote.isPresent());
        assertEquals("Title 1", foundNote.get().getTitle());
        verify(noteRepository, times(1)).findById(1L);
    }
}