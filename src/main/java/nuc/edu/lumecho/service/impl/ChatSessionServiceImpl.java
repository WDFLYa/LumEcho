package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.mapper.ChatSessionMapper;
import nuc.edu.lumecho.model.entity.ChatSession;
import nuc.edu.lumecho.service.ChatSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ChatSessionServiceImpl implements ChatSessionService {

    @Resource
    private ChatSessionMapper chatSessionMapper;

    @Override
    public ChatSession getOrCreateSession(Long userId, Long photographerId) {
        ChatSession session = chatSessionMapper.selectByUserAndPhotographer(userId, photographerId);
        if (session == null) {
            session = new ChatSession();
            session.setUserId(userId);
            session.setPhotographerId(photographerId);
            chatSessionMapper.insertSession(session);
        }
        return session;
    }
}