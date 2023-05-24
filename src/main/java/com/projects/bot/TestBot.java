package com.projects.bot;





import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;


public class TestBot extends TelegramLongPollingBot {

    private static final String[][] links = new String[][]{{"������������ ���� ������","https://imet.spbstu.ru/"},{"����� � ����������","https://t.me/imetspbstu"}
            ,{"������ ���������","https://vk.com/imet.spbstu"}};
    private static final String[] mainTopics = new String[]{"�����������\uD83D\uDE0B", "���������\uD83C\uDFE0",
            "���������\uD83D\uDCB5","������� ��������\uD83D\uDCC3","�������������� ����������\uD83C\uDFC6","�������� ������\uD83D\uDD17"};
    private  static  final String[] entranceTopics = new String[] {"��� ������ ���������?","��������������� ���������","����� �������", "�� ���",
    "�� ������������� ����������"};
    private  static  final String generalQuestions = "1. �� ������� ����������� ����� ������ ���������\n" +
            "2. ��������� ���� �� � ����������� ����������\n" +
            "3. ���� ���������� ���������� �������\n" +
            "4. ���� ���������� �������� � ����������\n" +
            "5. ���������� �� ������������ ��������������� ���. �������\n" +
            "6. ���������� ���� �� � ����������� ����������\n" +
            "7. ���� �������������� ����������\n" +
            "8. ��������, ����� � ����� �������� ��������\n";



    static private final ReplyKeyboardMarkup keyboardHello = new ReplyKeyboardMarkup();
    static private final InlineKeyboardMarkup keyboardEntrance = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup keyboardGenQuest = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup linksKeyboard = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup admission_1 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup admission_2 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup admission_3 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup admission_4 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup admission_5 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup educationalPrograms = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup genQuest_1 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup genQuest_34 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup genQuest_5 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup genQuest_6 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup genQuest_7 = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup examKeyboard = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup entranceTestKeyboard = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup dormKeyboard = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup scholarshipKeyboard = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup costKeyboard = new InlineKeyboardMarkup();
    static private final  InlineKeyboardMarkup achievementsKeyboard = new InlineKeyboardMarkup();




    static{
        String[] questions = new String[9];
        questions[0] = "�����";
        for (int i = 1; i < questions.length; ++i) {
            questions[i] = Integer.toString(i);
        }
        assembleKeyboard(keyboardGenQuest, questions);
        assembleKeyboard(keyboardEntrance,entranceTopics);

        //achievements
        List<List<InlineKeyboardButton>> achievementsRow = new ArrayList<>();
        achievementsRow.add(List.of(InlineKeyboardButton.builder().text("��� ����������� �������������� ����������").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/individual-achievements/").build()));
        achievementsKeyboard.setKeyboard(achievementsRow);
        //cost
        List<List<InlineKeyboardButton>> costRow = new ArrayList<>();
        costRow.add(List.of(InlineKeyboardButton.builder().text("��������� �������� � �������").url("https://www.spbstu.ru/upload/sveden/2021/paid-services/order-976-21-04-2023.pdf").build()));
        costRow.add(List.of(InlineKeyboardButton.builder().text("��� ������� ��������������� ������").url("https://www.spbstu.ru/education/students/paid-educational-services/").build()));
        costKeyboard.setKeyboard(costRow);
        //scholarship
        List<List<InlineKeyboardButton>> scholarshipRow = new ArrayList<>();
        scholarshipRow.add(List.of(InlineKeyboardButton.builder().text("���������� � ����������").url("http://x92176ra.beget.tech/social-security/scholarships/").build()));
        scholarshipKeyboard.setKeyboard(scholarshipRow);
        //links
        List<List<InlineKeyboardButton>> linksRows = new ArrayList<>();
        for (String[] link : links) {
            linksRows.add(List.of(InlineKeyboardButton.builder().text(link[0]).url(link[1]).build()));
        }
        linksKeyboard.setKeyboard(linksRows);
        //dorm
        List<List<InlineKeyboardButton>> dormRow = new ArrayList<>();
        dormRow.add(List.of(InlineKeyboardButton.builder().text("\uD83C\uDFD8").url("https://www.spbstu.ru/students/social-security/hostel/").build()));
        dormKeyboard.setKeyboard(dormRow);
        //entrance test
        List<List<InlineKeyboardButton>> entranceTestRow = new ArrayList<>();
        entranceTestRow.add(List.of(InlineKeyboardButton.builder().text("���������� ������������� ��������� (�� ���� ��.��� ���) ").url("https://www.spbstu.ru/abit/bachelor/entrance-test/the-schedule-of-entrance-examinations/").build()));
        entranceTestRow.add(List.of(InlineKeyboardButton.builder().text("���������� ������������� ��������� (�� ���� ��.���� ���) ").url("https://www.spbstu.ru/abit/bachelor/entrance-test/raspisanie-vstupitelnykh-ispytaniy-spo/").build()));

        entranceTestRow.add(List.of(InlineKeyboardButton.builder().text("������� �������").url("https://www.spbstu.ru/abit/bachelor/entrance-test/obraztsy-zadaniy-vstupitelnykh-ispytaniy/").build()));
        entranceTestRow.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        entranceTestKeyboard.setKeyboard(entranceTestRow);
        //exam keyboard

        List<List<InlineKeyboardButton>> examRow = new ArrayList<>();
        examRow.add(List.of(InlineKeyboardButton.builder().text("���� ������ � ����� ������ ����������").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/plan-the-calendar-of-admission-to-the-1st-year/").build()));
        examRow.add(List.of(InlineKeyboardButton.builder().text("�������� ������������� ��������� �� ... ����������� ").url("https://www.spbstu.ru/abit/bachelor/entrance-test/the-list-of-entrance-examinations/").build()));
        examRow.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        examKeyboard.setKeyboard(examRow);
        //admission
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("����������� �������� ��������").url("https://enroll.spbstu.ru/sign-up").build()));
        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("������� ������ ���������� (�� �����)").callbackData("�����������������������").build()));

        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("����-��������� ������ �� ������ ����").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/plan-the-calendar-of-admission-to-the-1st-year/").build()));
        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("������ ������� ������ ����������").url("https://www.spbstu.ru/abit/bachelor/apply/methods-of-submission-of-documents/").build()));

        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        admission_1.setKeyboard(keyboardRows);

        //admission_2
        List<List<InlineKeyboardButton>> keyboardRows2 = new ArrayList<>();
        keyboardRows2.add(List.of(InlineKeyboardButton.builder().text("����������� �������� ��������").url("https://enroll.spbstu.ru/sign-up").build()));
        keyboardRows2.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("������������������������").build()));
        keyboardRows2.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        admission_2.setKeyboard(keyboardRows2);

        //admission_3
        List<List<InlineKeyboardButton>> keyboardRows3 = new ArrayList<>();
        keyboardRows3.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("����������������").build()));
        keyboardRows3.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        admission_3.setKeyboard(keyboardRows3);
        //admission_4
        List<List<InlineKeyboardButton>> keyboardRows4 = new ArrayList<>();
        keyboardRows4.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("����������������������").build()));
        keyboardRows4.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        admission_4.setKeyboard(keyboardRows4);
//admission_5
        List<List<InlineKeyboardButton>> keyboardRows5 = new ArrayList<>();
        keyboardRows5.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("��� ������?").build()));
        keyboardRows5.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        admission_5.setKeyboard(keyboardRows5);
        //educational programs
        List<List<InlineKeyboardButton>> rowsPrograms = new ArrayList<>();
        rowsPrograms.add(List.of(InlineKeyboardButton.builder().text("��������������� ��������� (������)").url("https://imet.spbstu.ru/edu/").build()));
        rowsPrograms.add(List.of(InlineKeyboardButton.builder().text("��������� ���������� �� ��������������� ����������").url("https://www.spbstu.ru/sveden/education/documents-educational-process-educational-organization/").build()));
        rowsPrograms.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        educationalPrograms.setKeyboard(rowsPrograms);
//genQuest_1
        List<List<InlineKeyboardButton>> genQuestRow = new ArrayList<>();
        genQuestRow.add(List.of(InlineKeyboardButton.builder().text("������� ��������� ����� ������� ���").url("https://www.spbstu.ru/abit/bachelor/entrance-test/average-passing-scores-of-previous-years/").build()));
        genQuestRow.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        genQuest_1.setKeyboard(genQuestRow);
        //genQuest_34
        List<List<InlineKeyboardButton>> genQuest34Row = new ArrayList<>();
        genQuest34Row.add(List.of(InlineKeyboardButton.builder().text("����-��������� ������ �� ������ ����").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/plan-the-calendar-of-admission-to-the-1st-year/").build()));
        genQuest34Row.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        genQuest_34.setKeyboard(genQuest34Row);
        //genQuest_5
        List<List<InlineKeyboardButton>> genQuest5Row = new ArrayList<>();
        genQuest5Row.add(List.of(InlineKeyboardButton.builder().text("��� ������").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/informatsiya-ob-obyazatelnom-meditsinskom-osmotre/").build()));
        genQuest5Row.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        genQuest_5.setKeyboard(genQuest5Row);
        //genQuest_6
        List<List<InlineKeyboardButton>> genQuest6Row = new ArrayList<>();
        genQuest6Row.add(List.of(InlineKeyboardButton.builder().text("���������� ���� ��� ������ �� ��������� �������� �����������").url("https://www.spbstu.ru/abit/bachelor/apply/perechen-napravleniy-podgotovki/").build()));
        genQuest6Row.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        genQuest_6.setKeyboard(genQuest6Row);
        //genQuest_7
        List<List<InlineKeyboardButton>> genQuest7Row = new ArrayList<>();
        genQuest7Row.add(List.of(InlineKeyboardButton.builder().text("���� �������������� ����������").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/individual-achievements/").build()));
        genQuest7Row.add(List.of(InlineKeyboardButton.builder().text("�����").callbackData("�����").build()));
        genQuest_7.setKeyboard(genQuest7Row);



    }

    static Long id;


    public TestBot(String botToken) {
        super(botToken);
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    void sendMenu(Long who, String txt, InlineKeyboardMarkup kb) {

        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .replyMarkup(kb)
                .parseMode("HTML")
                .text(txt)
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    static void assembleKeyboard(InlineKeyboardMarkup kb, String[] buttonNames) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        for (String topic : buttonNames) {
            keyboardRows.add(List.of(InlineKeyboardButton.builder().text(topic).callbackData(topic).build()));
        }
        kb.setKeyboard(keyboardRows);
    }

    void sendMenu(Long who, String txt, ReplyKeyboardMarkup kb, String[] buttonNames) {
        // kb.setResizeKeyboard(true);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        for (String topic : buttonNames) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton(topic));
        }
        kb.setKeyboard(keyboardRows);

        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .replyMarkup(kb)
                .parseMode("HTML")
                .text(txt)
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean sendPhoto(InlineKeyboardMarkup kb, String chatId, String pathToPhoto) {
        try {
            SendPhoto sendPhotoRequest = new SendPhoto();
            // Set destination chat id
            sendPhotoRequest.setChatId(chatId);
            // Set the photo file as a new photo (You can also use InputStream with a constructor overload)
            sendPhotoRequest.setPhoto(new InputFile(new java.io.File(pathToPhoto)));
            sendPhotoRequest.setReplyMarkup(kb);
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    Stack<PrevMessage> prevMessageStack = new Stack<>();
    PrevMessage prevMessage;

    private void buttonTap(Long id, String queryId, String data, Integer msgId) {

        switch (data) {
            case "�����":
                PrevMessage prev = prevMessageStack.pop();
                if (prev.prevPhoto_ == null) {
                    sendMenu(id, prev.prevText_, prev.prevMarkup_);
                } else {
                    sendPhoto(prev.prevMarkup_, id.toString(), prev.prevPhoto_);
                }
                break;
            case "��� ������ ���������?":
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> ����������� </b>"));
                sendMenu(id, "������ ��������� ����� ����������� ���������. ���� ��� �������� ������ ��������� ������, ����� ���� ����������� �������� ��������.", admission_1);
                break;
            case "�����������������������":
                prevMessageStack.add(new PrevMessage(admission_1, "������ ��������� ����� ����������� ���������. ���� ��� �������� ������ ��������� ������, ����� ���� ����������� �������� ��������."));
                sendText(id, "1 ������ ����� ����� ������������������ �� ����� ����������� �������� �������� ����� ");
                sendPhoto(admission_2, id.toString(), "screenshots/registration_page.png");
                break;
            case "������������������������":
                prevMessageStack.add(new PrevMessage("screenshots/registration_page.png", admission_2, "1 ������ ����� ����� ������������������ �� ����� ����������� �������� �������� ����� "));
                sendText(id, "2. ������ ����� ����������� ����������� � ������, ������� ������ ������ � ��� �� �����");
                sendPhoto(admission_3, id.toString(), "screenshots/confirmation.png");
                break;
            case "����������������": {
                prevMessageStack.add(new PrevMessage("screenshots/confirmation.png", admission_3, "2. ������ ����� ����������� ����������� � ������, ������� ������ ������ � ��� �� �����"));
                sendText(id, "3. ��� ������������ �� �������� ����� �������� �������� � ���������� �� �������� �����������." +
                        "\n ��� ����� ������ �� ������� ������ \"�����\" � ��������� ������ �����������");
                sendPhoto(admission_4, id.toString(), "screenshots/form.png");
                break;
            }
            case "����������������������": {
                prevMessageStack.add(new PrevMessage("screenshots/form.png", admission_4, "3. ��� ������������ �� �������� ����� �������� �������� � ���������� �� �������� �����������."));
                sendText(id, "4. ������ ����� ������ ��������� �� �����������");
                sendPhoto(admission_5, id.toString(), "screenshots/statement.png");
                break;
            }
            case "��������������� ���������": {
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> ����������� </b>"));
                sendMenu(id, "1 - ��������������� ��������� �� ������������ ������� ( � ����������� ���������� ���������" +
                        "\n 2 - �������� �� ��������������� ���������� � ������� ������ �� ���� ������������ ", educationalPrograms);
                break;
            }

            case "����� �������":
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> ����������� </b>"));
                sendMenu(id, generalQuestions, keyboardGenQuest);
                break;
            case "1": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendText(id,"������������ ���������� �����������, �� ������� ���������� ����� ������ ��������� � ���, � 4.");
                break;
            }
            case "2": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"��������� ����� �������� ���� ���������� �������� ������ ����� ����, ��� ��� ����������� " +
                        "����������� � ������������ ����������. ������� �������� ������ ��������� ����� ������� ���, �, �� ����," +
                        "��� ������ �� ������.",genQuest_1);
                break;
            }
            case "3": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"����� ����� ����� ���� ���������� ���������� �������",genQuest_34);
                break;
            }
            case "4": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"����� ����� ����� ���� ���������� �������� � ����������",genQuest_34);
                break;
            }
            case "5": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"\uD83D\uDD17��� ����������� �� ����� ������������� ��������� ����������� ������",genQuest_5);
                break;
            }
            case "6": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"\uD83D\uDD17 ���������� ���� �� � ����������� ����������",genQuest_6);
                break;
            }
            case "7": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"\uD83D\uDD17���� �������������� ����������",genQuest_7);
                break;
            }
            case "8": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendText(id,"����� �������� �������� ������: \n" +
                        "��.���������������, �. 29, 3-� ������� ������, ���. 104.\n" +
                        "����� ������: ����������� � ������� � 10.00 �� 16.00 (���� � 13.00 �� 14.00)\n" +
                        "\n" +
                        "���������� �������: +79219005542\n" +
                        "�����: abit.imet@spbstu.ru"
                );
                break;
            }
            case "�� ���":
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> ����������� </b>"));
                sendMenu(id,"�� ���",examKeyboard);
                break;
            case "�� ������������� ����������":
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> ����������� </b>"));
                sendMenu(id,"�� ������������� ����������",entranceTestKeyboard);
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();
        try {
            execute(close);

        } catch (TelegramApiException e) {
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

        if (update.hasCallbackQuery()) {
            var callBack = update.getCallbackQuery();
            buttonTap(id, callBack.getId(), callBack.getData(), callBack.getMessage().getMessageId());
        }
        if (update.hasMessage()) {
            var msg = update.getMessage();
            var user = msg.getFrom();
            id = user.getId();
            if (msg.isCommand()) {
                switch (msg.getText()) {
                    case "/start" -> {
                        sendText(id, "������, ������� ����������, � �������� ��������, ������������� " +
                                "���������� ����� ���������� ��� ����. � ������, ����� ������ ������ �� ���������� �������.\n ��� ��� " +
                                "������ ����, ������� ���� ���������� -> ");
                        sendMenu(id, "<b>������ ����:</b>", keyboardHello, mainTopics);
                    }
                }
                return;
            } else if (msg.hasText()) {
                switch (msg.getText()) {
                    case "�����������\uD83D\uDE0B" -> {
                        sendMenu(id, "<b> ����������� </b>", keyboardEntrance);
                    }
                    case "�������� ������\uD83D\uDD17" -> {
                        sendMenu(id, "�������� ������:", linksKeyboard);
                    }
                    case "���������\uD83C\uDFE0" -> {
                        sendMenu(id,"���������� �� ����������",dormKeyboard);
                    }
                    case "���������\uD83D\uDCB5" -> {
                        sendMenu(id,"���������", scholarshipKeyboard);
                    }
                    case "������� ��������\uD83D\uDCC3" -> {
                        sendMenu(id,"��������� �������� � ������� �� ... ����������� ���������� (�������) ",costKeyboard);
                    }
                    case "�������������� ����������\uD83C\uDFC6" -> {
                        sendMenu(id,"���� �������������� ����������",achievementsKeyboard);
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
