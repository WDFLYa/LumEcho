package nuc.edu.lumecho.controller.post;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping("/publish")
    public Result publishPost(PublishPostRequest publishPostRequest) {
        postService.publishPost(publishPostRequest);
        return Result.ok();
    }

}
