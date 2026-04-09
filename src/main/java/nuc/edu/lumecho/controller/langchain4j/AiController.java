package nuc.edu.lumecho.controller.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private ChatLanguageModel chatModel;

    @PostMapping("/polish")
    public String polish(@RequestBody PolishRequest request) {
        String prompt = """
        请你只对下面的文本进行润色优化：
        1. 严格保持原文意思，绝对不添加新内容、不回答、不评价
        2. 优化语句通顺流畅，语气自然亲切、友好可爱
        3. 只做文字润色，不额外创作、不脑补内容
        4. 可以在结尾**自由加一个可爱小符号**，比如 ✨ 🥰 🌸 💫 ~
        5. 只返回润色后的文本，不要多余话

        待润色文本：
        %s
        """.formatted(request.getContent());

        return chatModel.generate(prompt);
    }

    public static class PolishRequest {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}