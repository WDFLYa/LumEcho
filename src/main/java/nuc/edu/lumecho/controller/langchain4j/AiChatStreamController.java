package nuc.edu.lumecho.controller.langchain4j;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.output.Response;
import nuc.edu.lumecho.common.RedisKeyConstants;
import nuc.edu.lumecho.model.entity.ChatMessage;
import nuc.edu.lumecho.model.entity.ChatSession;
import nuc.edu.lumecho.service.ChatMessageService;
import nuc.edu.lumecho.service.ChatSessionService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/ai")
public class AiChatStreamController {

    @Resource
    private ChatSessionService chatSessionService;

    @Resource
    private ChatMessageService chatMessageService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private HttpServletRequest request;

    private final QwenStreamingChatModel streamingModel = QwenStreamingChatModel.builder()
            .apiKey("sk-34e82939a9384e8bb68ccf42e29ba32c")
            .modelName("qwen-plus")
            .temperature(0.7f)
            .build();

    // 从请求头解析 userId（流式接口唯一可靠方式）
    private Long getCurrentUserId() {
        String authHeader = request.getHeader("Authorization");


        String token = authHeader.substring(7);
        String key = RedisKeyConstants.USER_TOKEN_KEY + token;
        String userIdStr = stringRedisTemplate.opsForValue().get(key);



        return Long.valueOf(userIdStr);
    }

    @PostMapping(value = "/chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatStream(@RequestBody ChatRequest request) {
        SseEmitter emitter = new SseEmitter(1800000L);

        String prompt = "你是一位活泼开朗、超会聊天的专业摄影师！说话轻松有趣、亲切友好，不生硬、不呆板。"
                + "精通构图、光线、相机参数、器材推荐、后期修图等所有摄影知识。"
                + "回答简洁清楚、带点活力，耐心又好懂，像朋友一样帮忙～只回答摄影相关问题哦！\n";

        // ✅ 这里获取真实 userId
        Long userId = getCurrentUserId();
        Long photographerId = request.getPhotographerId();

        // ✅ 传入真实 userId
        ChatSession session = chatSessionService.getOrCreateSession(userId, photographerId);

        if (session == null || session.getId() == null) {
            try {
                emitter.send("data: [DONE]\n\n");
            } catch (Exception ignored) {
            }
            emitter.complete();
            return emitter;
        }

        List<ChatMessage> historyList = chatMessageService.getHistory(session.getId(), 10);
        StringBuilder allPrompt = new StringBuilder(prompt);
        for (ChatMessage msg : historyList) {
            allPrompt.append(msg.getRole()).append("：").append(msg.getContent()).append("\n");
        }
        allPrompt.append("用户：").append(request.getContent());

        chatMessageService.saveMessage(session.getId(), "user", request.getContent());

        StringBuilder aiAnswer = new StringBuilder();

        streamingModel.generate(allPrompt.toString(), new StreamingResponseHandler<AiMessage>() {
            @Override
            public void onNext(String token) {
                try {
                    aiAnswer.append(token);
                    emitter.send("data: " + token + "\n\n");
                } catch (Exception e) {
                    safeSaveAndComplete();
                }
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                safeSaveAndComplete();
            }

            @Override
            public void onError(Throwable throwable) {
                safeSaveAndComplete();
            }

            private void safeSaveAndComplete() {
                try {
                    if (aiAnswer.length() > 0) {
                        chatMessageService.saveMessage(session.getId(), "assistant", aiAnswer.toString());
                    }
                    emitter.send("data: [DONE]\n\n");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    emitter.complete();
                }
            }
        });

        return emitter;
    }

    @PostMapping("/chat-history")
    public List<ChatMessage> chatHistory(@RequestBody ChatRequest request) {
        // ✅ 这里也用真实 userId
        Long userId = getCurrentUserId();
        Long photographerId = request.getPhotographerId();
        ChatSession session = chatSessionService.getOrCreateSession(userId, photographerId);
        if (session == null || session.getId() == null) return null;
        return chatMessageService.getHistory(session.getId(), 100);
    }

    public static class ChatRequest {
        private Long photographerId;
        private String content;

        public Long getPhotographerId() {
            return photographerId;
        }

        public void setPhotographerId(Long photographerId) {
            this.photographerId = photographerId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}