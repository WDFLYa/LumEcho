package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> getHistory(Long sessionId, int limit);
    void saveMessage(Long sessionId, String role, String content);
}