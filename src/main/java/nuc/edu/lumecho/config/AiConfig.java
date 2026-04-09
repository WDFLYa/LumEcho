package nuc.edu.lumecho.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.dashscope.QwenChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Bean
    public ChatLanguageModel chatModel() {
        return QwenChatModel.builder()
                .apiKey("sk-34e82939a9384e8bb68ccf42e29ba32c")
                .modelName("qwen-plus")
                .temperature(0.7f)
                .build();
    }
}