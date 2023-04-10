package com.projects.bot;





import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TestBot extends TelegramLongPollingBot {

    private static final String[] mainTopics = new String[]{"Поступление\uD83D\uDE0B", "Общежитие\uD83C\uDFE0",
            "Стипендии\uD83D\uDCB5","Платное обучение\uD83D\uDCC3","Индивидуальные достижения\uD83C\uDFC6"};

   //private static Map<Long,Boolean> users;

    private InlineKeyboardMarkup keyboardM1;
    private InlineKeyboardMarkup keyboardM2;

    static Long id;


    public TestBot(String botToken) {
        super(botToken);
    }

    public void sendText(Long who, String what){
        SendMessage sm  = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        try{
            execute(sm);
        }
        catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }

    void sendMenu(Long who, String txt, InlineKeyboardMarkup kb ){
        SendMessage sm  = SendMessage.builder()
                .chatId(who.toString())
                .replyMarkup(kb)
                .parseMode("HTML")
                .text(txt)
                .build();
        try{
            execute(sm);
        }
        catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }

    void sendMenu(Long who, String txt, ReplyKeyboardMarkup kb, String[] buttonNames ){
        kb.setResizeKeyboard(true);
        kb.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        for(String topic : buttonNames){
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton(topic));
        }
        kb.setKeyboard(keyboardRows);

        SendMessage sm  = SendMessage.builder()
                .chatId(who.toString())
                .replyMarkup(kb)
                .parseMode("HTML")
                .text(txt)
                .build();
        try{
            execute(sm);
        }
        catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }




    private void buttonTap(Long id, String queryId, String data, int msgId){
        EditMessageText newTxt = EditMessageText.builder()
                .chatId(id.toString())
                .messageId(msgId).text("")
                .build();
        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                .chatId(id.toString())
                .messageId(msgId).build();
        if(data.equals("next")){
            newTxt.setText("MENU 2");
            newKb.setReplyMarkup((keyboardM2));
        }
        else if(data.equals("back")){
            newTxt.setText("MENU 1");
            newKb.setReplyMarkup((keyboardM1));
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();
        try {
            execute(close);
            execute(newTxt);
            execute(newKb);

        }
        catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botApi = new TelegramBotsApi(DefaultBotSession.class);
        TestBot bot = new TestBot("5954633966:AAGhptcZ1tuFhM5K5qlbKv725rXMrqCz7IE");
        botApi.registerBot(bot);
    }



    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery()){
            var callBack = update.getCallbackQuery();
            buttonTap(id,callBack.getId(),callBack.getData(),callBack.getMessage().getMessageId());
        }
        if(update.hasMessage()) {
            var msg = update.getMessage();
            var user = msg.getFrom();
            id = user.getId();

            if (msg.isCommand()) {
                switch (msg.getText()) {
                    case "/hello" -> {
                        sendText(id,"Привет, дорогой абитуриент, я цифровой советник, разработанный " +
                                "студентами СПБПУ специально для тебя. Я создан, чтобы давать ответы на популярные вопросы.\n Так что " +
                                "выбери тему, которая тебя интересует -> " );
                        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
                        sendMenu(id,"<b>Выбери тему:</b>",replyKeyboard, mainTopics);
                    }
                    case "/menu" -> {
                        var next = InlineKeyboardButton.builder()
                                .text("Next").callbackData("next")
                                .build();
                        keyboardM1 = InlineKeyboardMarkup.builder()
                                .keyboardRow(List.of(next)).build();
                        sendMenu(id, "<b> Menu1 </b>", keyboardM1);
                    }
                }
                return;
            }
            else if(msg.hasText()){
                switch (msg.getText()){
                    case "Поступление\uD83D\uDE0B":{
                        var genQuestions = InlineKeyboardButton.builder()
                                .text("<b>Общие вопросы</b>").callbackData("genQuestions")
                                .build();
                        var ege = InlineKeyboardButton.builder()
                                .text("<b>По ЕГЭ</b>").callbackData("ege")
                                .build();
                        var back = InlineKeyboardButton.builder()
                                .text("<b>Назад к темам</b>").callbackData("back")
                                .build();

//                        var url = InlineKeyboardButton.builder()
//                                .text("Оффициальный сайт СПБПУ")
//                                .url("https://www.spbstu.ru/")
//                                .build();
                        keyboardM1 = InlineKeyboardMarkup.builder()
                                .keyboardRow(List.of(genQuestions))
                                .keyboardRow(List.of(ege))
                                .keyboardRow(List.of(back))
                                .build();

                        sendText(id,"Привет, дорогой абитуриент, я цифровой советник, разработанный " +
                                "студентами СПБПУ специально для тебя. Я создан, чтобы давать ответы на популярные вопросы.\n Так что " +
                                "выбери тему, которая тебя интересует -> " );
                        sendMenu(id,"<b> Интересующая тебя тема </b>",keyboardM1);
                    }

                }
            }

            System.out.println(user.getUserName() + " wrote " + msg.getText());
        }

    }

    @Override
    public String getBotUsername() {
        return "@stingy_deni_bot";
    }
}
