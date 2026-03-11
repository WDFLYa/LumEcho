package nuc.edu.lumecho.model.vo;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentNode {
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private String content;
    private String createTimeAgo;
    private List<CommentNode> children = new ArrayList<>();
}