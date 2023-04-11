package com.projects.bot;





import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;


public class TestBot extends TelegramLongPollingBot {

    private static final String[] mainTopics = new String[]{"Поступление\uD83D\uDE0B", "Общежитие\uD83C\uDFE0",
            "Стипендии\uD83D\uDCB5","Платное обучение\uD83D\uDCC3","Индивидуальные достижения\uD83C\uDFC6"};
    private final String[] entranceTopics = new String[] {"Общие вопросы", "По ЕГЭ",
    "По вступительным испытаниям", "БВИ", "Для льготных категорий лиц", "Для иностранных граждан"};
    private final String generalQuestions = "1. На сколько направлений можно подать документы\n" +
            "2. Проходной балл на … направление подготовки\n" +
            "3. Информация о … направлении подготовки\n" +
            "4. Даты публикации конкурсных списков\n" +
            "5. Даты публикации приказов о зачислении\n" +
            "6. Информация об обязательном предварительном мед. осмотре\n" +
            "7. Количество мест на … направление подготовки\n" +
            "8. Перечень вступительных испытаний (ЕГЭ) по … направлению подготовки\n" +
            "9. Способы подачи документов\n" +
            "10. Учет индивидуальных достижений\n" +
            "11. Телефоны, почта и адрес приемной комиссии\n" +
            "12. Как отслеживать свой статус после подачи документов";
    private final String egeQuestions = "1. Даты начала и конца приема документов\n" +
            "2. Перечень необходимых документов\n";


    ReplyKeyboardMarkup keyboardHello;
    private InlineKeyboardMarkup keyboardEntrance;
    private InlineKeyboardMarkup keyboardGenQuest;

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

    void sendMenu(Long who, String txt, InlineKeyboardMarkup kb){

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

    void assembleKeyboard(InlineKeyboardMarkup kb,String[] buttonNames ){
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        for (String topic : buttonNames) {
            keyboardRows.add(List.of(InlineKeyboardButton.builder().text(topic).callbackData(topic).build()));
        }
        kb.setKeyboard(keyboardRows);
    }

    void sendMenu(Long who, String txt, ReplyKeyboardMarkup kb, String[] buttonNames ){
        // kb.setResizeKeyboard(true);

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
//        if(data.equals("next")){
//            newTxt.setText("MENU 2");
//            newKb.setReplyMarkup((keyboardM2));
//        }
//        else if(data.equals("back")){
//            newTxt.setText("MENU 1");
//            newKb.setReplyMarkup((keyboardEntrance));
//        }
        switch(data){
//            case "Назад":
//                newTxt.setText("Поступление");
//                newKb.setReplyMarkup((keyboardEntrance));
            case "Общие вопросы":
                keyboardGenQuest = new InlineKeyboardMarkup();
                String[] questions = new String[12];
                for (int i = 0; i < questions.length; ++i) {
                    questions[i] = Integer.toString(i + 1);
                }
                assembleKeyboard(keyboardGenQuest, questions);
                newTxt.setText(generalQuestions);
                newKb.setReplyMarkup(keyboardGenQuest);
            case "По ЕГЭ":

            case "По вступительным испытаниям":



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
                        keyboardHello = new ReplyKeyboardMarkup();
                        sendMenu(id,"<b>Выбери тему:</b>",keyboardHello, mainTopics);
                    }
//                    case "/menu" -> {
//                        var next = InlineKeyboardButton.builder()
//                                .text("Next").callbackData("next")
//                                .build();
//                        keyboardM1 = InlineKeyboardMarkup.builder()
//                                .keyboardRow(List.of(next)).build();
//                        sendMenu(id, "<b> Menu1 </b>", keyboardM1);
//                    }
                }
                return;
            }
            else if(msg.hasText()){
                switch (msg.getText()) {
                    case "Поступление\uD83D\uDE0B" -> {
                        keyboardEntrance = new InlineKeyboardMarkup();
                        assembleKeyboard(keyboardEntrance,entranceTopics);
                        sendMenu(id, "<b> Интересующая тебя тема </b>", keyboardEntrance);
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
