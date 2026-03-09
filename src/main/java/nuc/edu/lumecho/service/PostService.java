package nuc.edu.lumecho.service;
import nuc.edu.lumecho.model.dto.request.post.PostIdRequest;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostRequest;


public interface PostService {
    void publishPost(PublishPostRequest publishPostRequest);
    void updatePost(UpdatePostRequest updatePostRequest);
    void deletePost(PostIdRequest postIdRequest);

}
