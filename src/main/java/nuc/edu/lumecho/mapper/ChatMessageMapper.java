package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    @Select("SELECT * FROM chat_message WHERE session_id = #{sessionId} ORDER BY create_time ASC LIMIT #{limit}")
    List<ChatMessage> selectHistory(@Param("sessionId") Long sessionId, @Param("limit") int limit);

    @Insert("INSERT INTO chat_message(session_id, role, content) VALUES(#{sessionId}, #{role}, #{content})")
    int insertMessage(ChatMessage message);
}