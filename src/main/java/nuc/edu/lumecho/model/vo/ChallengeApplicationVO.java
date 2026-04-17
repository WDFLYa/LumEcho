package nuc.edu.lumecho.model.vo;
import lombok.Data;
import nuc.edu.lumecho.model.entity.ChallengeApplication;
import nuc.edu.lumecho.model.entity.User;

@Data
public class ChallengeApplicationVO {
    private ChallengeApplication application;
    private String username;
    private String avatar;
}