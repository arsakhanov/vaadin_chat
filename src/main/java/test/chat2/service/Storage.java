package test.chat2.service;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import org.springframework.stereotype.Component;
import test.chat2.model.ChatMessage;
import test.chat2.service.impl.ChatMessageServiceImpl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Storage {

    private final static int N = 5;
    @Getter
    private Queue<ChatMessage> messages = new ConcurrentLinkedQueue<>();

    public void setMessages(ChatMessage message) {
        if(messages.size() < N){
            messages.add(message);
        } else {
            messages.remove();
            messages.add(message);
        }
    }

    private ComponentEventBus eventBus = new ComponentEventBus(new Div());
    @Getter
    private Set<String> users = new HashSet<>();
    private final ChatMessageServiceImpl chatMessageService;

    public Storage(ChatMessageServiceImpl chatMessageService) {
        this.chatMessageService = chatMessageService;
    }
    public static class ChatEvent extends ComponentEvent<Div> {
        public ChatEvent() {
            super(new Div(), false);
        }
    }

    public void addRecord(String name, String message) {
        ChatMessage chatMessage = new ChatMessage(name, message);
        setMessages(chatMessage);
        eventBus.fireEvent(new ChatEvent());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        chatMessage.setDate(simpleDateFormat.format(date));
        chatMessageService.saveMessage(chatMessage);

    }

    public void addRecordJoined(String user) {
        setMessages(new ChatMessage("", user));
        eventBus.fireEvent(new ChatEvent());
        users.add(user);
    }

    public Registration attachListener(ComponentEventListener<ChatEvent> messageListener) {
        return eventBus.addListener(ChatEvent.class, messageListener);
    }

    public int size() {
        return messages.size();
    }
}
