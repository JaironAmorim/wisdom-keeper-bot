package com.jaironamorim.wisdomkeeperbot.service;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotService extends TelegramLongPollingBot {
    private final Gpt3Service gpt3Service;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        Message message;
        if(update.hasMessage()) {
            message = update.getMessage();
            sendMessage.setChatId(message.getChatId());
            if (message.hasText()) {
                String question = message.getText();
                if(question.equals("/start")){
                    String defautMasg = "Olá, sou o Wisdom Keeper Bot. Eu estou aqui para ajudar a responder perguntas e " +
                            "fornecer sabedoria com meus conhecimentos. Se tiver dúvidas, não hesite em perguntar!";
                    sendMessage.setText(defautMasg);
                    sendMessage(sendMessage);
                    return;
                }
                log.info("Question: "+ question);
                sendMessage.setText(gpt3Service.generateResponse(question, message.getChatId().toString()));
                log.info("Response: " + sendMessage.getText());
                sendMessage(sendMessage);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    public boolean sendMessage(SendMessage sendMessage){
        try {
            if(sendMessage.getText().isEmpty())
                return false;
            sendMessage.setParseMode(ParseMode.HTML);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.info("Erro ao enviar mensagem: ".concat(e.toString()));
        }
        return true;
    }
}
