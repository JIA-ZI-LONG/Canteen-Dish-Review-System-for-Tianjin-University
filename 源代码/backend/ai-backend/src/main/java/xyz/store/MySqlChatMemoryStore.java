package xyz.store;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.entity.ChatMessage;
import xyz.mapper.ChatMessageMapper;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Component
public class MySqlChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public List<dev.langchain4j.data.message.ChatMessage> getMessages(Object memoryId) {
        LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMessage::getMemoryId, String.valueOf(memoryId));

        ChatMessage chatMessage = chatMessageMapper.selectOne(queryWrapper);

        return Optional.ofNullable(chatMessage)
                .map(ChatMessage::getMessages)
                .map(ChatMessageDeserializer::messagesFromJson)
                .orElse(emptyList());
    }

    @Override
    public void updateMessages(Object memoryId, List<dev.langchain4j.data.message.ChatMessage> messages) {
        String messagesJson = ChatMessageSerializer.messagesToJson(messages);

        LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMessage::getMemoryId, String.valueOf(memoryId));
        ChatMessage existingMessage = chatMessageMapper.selectOne(queryWrapper);

        if (existingMessage != null) {
            existingMessage.setMessages(messagesJson);
            chatMessageMapper.updateById(existingMessage);
        } else {
            ChatMessage newChatMessage = new ChatMessage();
            newChatMessage.setMemoryId(String.valueOf(memoryId));
            newChatMessage.setMessages(messagesJson);
            chatMessageMapper.insert(newChatMessage);
        }
    }

    @Override
    public void deleteMessages(Object memoryId) {
        LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMessage::getMemoryId, String.valueOf(memoryId));
        chatMessageMapper.delete(queryWrapper);
    }
}