package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.ChatSession;

public interface ChatSessionService {
    ChatSession getOrCreateSession(Long userId, Long photographerId);
}