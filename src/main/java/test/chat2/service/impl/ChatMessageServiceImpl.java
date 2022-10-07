package test.chat2.service.impl;

import org.springframework.stereotype.Service;
import test.chat2.model.ChatMessage;
import test.chat2.repository.ChatMessageRepository;
import test.chat2.service.ChatMessageService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }
}
