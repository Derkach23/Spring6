package com.example.notes.service;

import com.example.model.Note;
import com.example.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    private MessageChannel requestChannel;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note addNote(Note note) {
        note.setCreatedAt(LocalDateTime.now());
        logRequest("Добавлена новая заметка: " + note.getTitle());
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        logRequest("Запрошен список всех заметок");
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(Long id) {
        logRequest("Запрошена заметка с ID: " + id);
        return noteRepository.findById(id);
    }

    public Note updateNote(Long id, Note newNote) {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setTitle(newNote.getTitle());
                    note.setContent(newNote.getContent());
                    logRequest("Обновлена заметка с ID: " + id);
                    return noteRepository.save(note);
                })
                .orElseThrow(() -> new RuntimeException("Заметка не найдена"));
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
        logRequest("Удалена заметка с ID: " + id);
    }

    private void logRequest(String message) {
        Message<String> logMessage = MessageBuilder.withPayload(message).build();
        requestChannel.send(logMessage);
    }

    private final LoggingService logger = LoggingService.getInstance();

    public void someMethod() {
        logger.log("Метод был вызван");
    }
}
