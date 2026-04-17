package nuc.edu.lumecho.mapper;


import nuc.edu.lumecho.model.entity.ChatSession;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatSessionMapper {
    @Select("SELECT * FROM chat_session WHERE user_id = #{userId} AND photographer_id = #{photographerId}")
    ChatSession selectByUserAndPhotographer(@Param("userId") Long userId, @Param("photographerId") Long photographerId);

    @Insert("INSERT INTO chat_session(user_id, photographer_id) VALUES(#{userId}, #{photographerId})")
    int insertSession(ChatSession session);
}