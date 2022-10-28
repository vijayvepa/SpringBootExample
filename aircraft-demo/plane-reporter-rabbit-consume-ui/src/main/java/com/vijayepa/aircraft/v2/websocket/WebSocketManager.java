package com.vijayepa.aircraft.v2.websocket;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WebSocketManager extends TextWebSocketHandler {
    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();

    public List<WebSocketSession> getWebSocketSessions() {
        return webSocketSessions;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        webSocketSessions.add(session);
        System.out.println("Connection established from  " + session + " @" + Instant.now());
    }

    @Override
    public void afterConnectionClosed(
            @NonNull WebSocketSession session,
            @NonNull CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
        System.out.println("Connection closed by " + session + "@" + Instant.now());
    }

    @Override
    protected void handleTextMessage(
            @NonNull WebSocketSession session,
            @NonNull TextMessage message) {
        try {

            System.out.println("Message received: '" + message + "' from " + session);


            notifyOtherSessions(session, message);

        } catch (Exception e) {
            System.out.println("Exception handling message: " + e.getLocalizedMessage());
        }
    }

    public void notifyAll(TextMessage textMessage) {
        webSocketSessions.forEach(webSocketSession ->
                notifySession(webSocketSession, textMessage)
        );
    }

    private void notifyOtherSessions(
            WebSocketSession session,
            TextMessage message) {
        webSocketSessions.forEach(sessionInList -> {

            sendMessageWhenNotSender(session, message, sessionInList);
        });
    }

    private static void sendMessageWhenNotSender(
            WebSocketSession session,
            TextMessage message,
            WebSocketSession sessionInList) {
        if (sessionInList != session) {
            notifySession(sessionInList, message);
            System.out.println("--> Sending message ' " + message + " ' to " + sessionInList);
        }
    }

    private static void notifySession(
            WebSocketSession sessionInList,
            TextMessage message) {
        try {
            sessionInList.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
