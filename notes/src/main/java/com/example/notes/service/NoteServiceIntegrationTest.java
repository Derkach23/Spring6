package com.example.notes.service;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // Чтобы после тестов база очищалась
class NoteServiceIntegrationTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @Test
    void testSaveAndFindNotes() {
        Note note = new Note();
        note.setTitle("New Note");
        note.setContent("This is a test note");
        noteRepository.save(note);

        List<Note> notes = noteService.findAllNotes();
        assertFalse(notes.isEmpty());
        assertEquals(1, notes.size());
        assertEquals("New Note", notes.get(0).getTitle());
    }
}