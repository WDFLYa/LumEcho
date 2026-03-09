package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    void insert(Post post);
}
