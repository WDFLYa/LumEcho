package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.mapper.ChatMessageMapper;
import nuc.edu.lumecho.model.entity.ChatMessage;
import nuc.edu.lumecho.service.ChatMessageService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Override
    public List<ChatMessage> getHistory(Long sessionId, int limit) {
        return chatMessageMapper.selectHistory(sessionId, limit);
    }

    @Override
    public void saveMessage(Long sessionId, String role, String content) {
        ChatMessage message = new ChatMessage();
        message.setSessionId(sessionId);
        message.setRole(role);
        message.setContent(content);
        chatMessageMapper.insertMessage(message);
    }
}