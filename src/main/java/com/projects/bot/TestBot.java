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

    private static final String[][] links = new String[][]{{"Оффициыльный сайт ИПМЭиТ","https://imet.spbstu.ru/"},{"Канал в телеграмме","https://t.me/imetspbstu"}
            ,{"Группа вконтакте","https://vk.com/imet.spbstu"}};
    private static final String[] mainTopics = new String[]{"Поступление\uD83D\uDE0B", "Общежитие\uD83C\uDFE0",
            "Стипендии\uD83D\uDCB5","Платное обучение\uD83D\uDCC3","Индивидуальные достижения\uD83C\uDFC6","Полезные ссылки\uD83D\uDD17"};
    private  static  final String[] entranceTopics = new String[] {"Как подать документы?","Образовательные программы","Общие вопросы", "По ЕГЭ",
    "По вступительным испытаниям"};
    private  static  final String generalQuestions = "1. На сколько направлений можно подать документы\n" +
            "2. Проходной балл на … направление подготовки\n" +
            "3. Даты публикации конкурсных списков\n" +
            "4. Даты публикации приказов о зачислении\n" +
            "5. Информация об обязательном предварительном мед. осмотре\n" +
            "6. Количество мест на … направление подготовки\n" +
            "7. Учет индивидуальных достижений\n" +
            "8. Телефоны, почта и адрес приемной комиссии\n";



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
        questions[0] = "Назад";
        for (int i = 1; i < questions.length; ++i) {
            questions[i] = Integer.toString(i);
        }
        assembleKeyboard(keyboardGenQuest, questions);
        assembleKeyboard(keyboardEntrance,entranceTopics);

        //achievements
        List<List<InlineKeyboardButton>> achievementsRow = new ArrayList<>();
        achievementsRow.add(List.of(InlineKeyboardButton.builder().text("Как учитываются индивидуальные достижения").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/individual-achievements/").build()));
        achievementsKeyboard.setKeyboard(achievementsRow);
        //cost
        List<List<InlineKeyboardButton>> costRow = new ArrayList<>();
        costRow.add(List.of(InlineKeyboardButton.builder().text("Стоимость обучения в семестр").url("https://www.spbstu.ru/upload/sveden/2021/paid-services/order-976-21-04-2023.pdf").build()));
        costRow.add(List.of(InlineKeyboardButton.builder().text("Все платные образовательные услуги").url("https://www.spbstu.ru/education/students/paid-educational-services/").build()));
        costKeyboard.setKeyboard(costRow);
        //scholarship
        List<List<InlineKeyboardButton>> scholarshipRow = new ArrayList<>();
        scholarshipRow.add(List.of(InlineKeyboardButton.builder().text("информация о степендиях").url("http://x92176ra.beget.tech/social-security/scholarships/").build()));
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
        entranceTestRow.add(List.of(InlineKeyboardButton.builder().text("Расписание вступительных испытаний (на базе ср.общ обр) ").url("https://www.spbstu.ru/abit/bachelor/entrance-test/the-schedule-of-entrance-examinations/").build()));
        entranceTestRow.add(List.of(InlineKeyboardButton.builder().text("Расписание вступительных испытаний (на базе ср.проф обр) ").url("https://www.spbstu.ru/abit/bachelor/entrance-test/raspisanie-vstupitelnykh-ispytaniy-spo/").build()));

        entranceTestRow.add(List.of(InlineKeyboardButton.builder().text("Образцы заданий").url("https://www.spbstu.ru/abit/bachelor/entrance-test/obraztsy-zadaniy-vstupitelnykh-ispytaniy/").build()));
        entranceTestRow.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        entranceTestKeyboard.setKeyboard(entranceTestRow);
        //exam keyboard

        List<List<InlineKeyboardButton>> examRow = new ArrayList<>();
        examRow.add(List.of(InlineKeyboardButton.builder().text("Даты начала и конца приема документов").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/plan-the-calendar-of-admission-to-the-1st-year/").build()));
        examRow.add(List.of(InlineKeyboardButton.builder().text("Перечень вступительных испытаний по ... направлению ").url("https://www.spbstu.ru/abit/bachelor/entrance-test/the-list-of-entrance-examinations/").build()));
        examRow.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        examKeyboard.setKeyboard(examRow);
        //admission
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("Электронная приемная комиссия").url("https://enroll.spbstu.ru/sign-up").build()));
        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("Процесс подачи документов (по шагам)").callbackData("ПроцессПодачиДокументов").build()));

        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("План-календарь приема на первый курс").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/plan-the-calendar-of-admission-to-the-1st-year/").build()));
        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("Другие спопобы подачи документов").url("https://www.spbstu.ru/abit/bachelor/apply/methods-of-submission-of-documents/").build()));

        keyboardRows.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        admission_1.setKeyboard(keyboardRows);

        //admission_2
        List<List<InlineKeyboardButton>> keyboardRows2 = new ArrayList<>();
        keyboardRows2.add(List.of(InlineKeyboardButton.builder().text("Электронная приемная комиссия").url("https://enroll.spbstu.ru/sign-up").build()));
        keyboardRows2.add(List.of(InlineKeyboardButton.builder().text("Далее").callbackData("подтверждениеРегистрации").build()));
        keyboardRows2.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        admission_2.setKeyboard(keyboardRows2);

        //admission_3
        List<List<InlineKeyboardButton>> keyboardRows3 = new ArrayList<>();
        keyboardRows3.add(List.of(InlineKeyboardButton.builder().text("Далее").callbackData("заполнениеАнкеты").build()));
        keyboardRows3.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        admission_3.setKeyboard(keyboardRows3);
        //admission_4
        List<List<InlineKeyboardButton>> keyboardRows4 = new ArrayList<>();
        keyboardRows4.add(List.of(InlineKeyboardButton.builder().text("Далее").callbackData("заявлениеНаПоступление").build()));
        keyboardRows4.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        admission_4.setKeyboard(keyboardRows4);
//admission_5
        List<List<InlineKeyboardButton>> keyboardRows5 = new ArrayList<>();
        keyboardRows5.add(List.of(InlineKeyboardButton.builder().text("Далее").callbackData("что дальше?").build()));
        keyboardRows5.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        admission_5.setKeyboard(keyboardRows5);
        //educational programs
        List<List<InlineKeyboardButton>> rowsPrograms = new ArrayList<>();
        rowsPrograms.add(List.of(InlineKeyboardButton.builder().text("Образовательные программы (ИПМЭиТ)").url("https://imet.spbstu.ru/edu/").build()));
        rowsPrograms.add(List.of(InlineKeyboardButton.builder().text("Подробная информация по образовательным программам").url("https://www.spbstu.ru/sveden/education/documents-educational-process-educational-organization/").build()));
        rowsPrograms.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        educationalPrograms.setKeyboard(rowsPrograms);
//genQuest_1
        List<List<InlineKeyboardButton>> genQuestRow = new ArrayList<>();
        genQuestRow.add(List.of(InlineKeyboardButton.builder().text("Средние проходные баллы прошлых лет").url("https://www.spbstu.ru/abit/bachelor/entrance-test/average-passing-scores-of-previous-years/").build()));
        genQuestRow.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        genQuest_1.setKeyboard(genQuestRow);
        //genQuest_34
        List<List<InlineKeyboardButton>> genQuest34Row = new ArrayList<>();
        genQuest34Row.add(List.of(InlineKeyboardButton.builder().text("План-календарь приема на первый курс").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/plan-the-calendar-of-admission-to-the-1st-year/").build()));
        genQuest34Row.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        genQuest_34.setKeyboard(genQuest34Row);
        //genQuest_5
        List<List<InlineKeyboardButton>> genQuest5Row = new ArrayList<>();
        genQuest5Row.add(List.of(InlineKeyboardButton.builder().text("Мед осмотр").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/informatsiya-ob-obyazatelnom-meditsinskom-osmotre/").build()));
        genQuest5Row.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        genQuest_5.setKeyboard(genQuest5Row);
        //genQuest_6
        List<List<InlineKeyboardButton>> genQuest6Row = new ArrayList<>();
        genQuest6Row.add(List.of(InlineKeyboardButton.builder().text("Количество мест для приема по различным условиям поступления").url("https://www.spbstu.ru/abit/bachelor/apply/perechen-napravleniy-podgotovki/").build()));
        genQuest6Row.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
        genQuest_6.setKeyboard(genQuest6Row);
        //genQuest_7
        List<List<InlineKeyboardButton>> genQuest7Row = new ArrayList<>();
        genQuest7Row.add(List.of(InlineKeyboardButton.builder().text("Учет индивидуальных достижений").url("https://www.spbstu.ru/abit/bachelor/oznakomitsya-with-the-regulations/individual-achievements/").build()));
        genQuest7Row.add(List.of(InlineKeyboardButton.builder().text("Назад").callbackData("Назад").build()));
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
            case "Назад":
                PrevMessage prev = prevMessageStack.pop();
                if (prev.prevPhoto_ == null) {
                    sendMenu(id, prev.prevText_, prev.prevMarkup_);
                } else {
                    sendPhoto(prev.prevMarkup_, id.toString(), prev.prevPhoto_);
                }
                break;
            case "Как подать документы?":
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> Поступление </b>"));
                sendMenu(id, "Подать документы можно несколькими способами. Этот бот помогает подать документы онлайн, через сайт Электронной приемной комиссии.", admission_1);
                break;
            case "ПроцессПодачиДокументов":
                prevMessageStack.add(new PrevMessage(admission_1, "Подать документы можно несколькими способами. Этот бот помогает подать документы онлайн, через сайт Электронной приемной комиссии."));
                sendText(id, "1 Первым шагом нужно зарегестрироваться на сайте электронной приемной комиссии СПБПУ ");
                sendPhoto(admission_2, id.toString(), "screenshots/registration_page.png");
                break;
            case "подтверждениеРегистрации":
                prevMessageStack.add(new PrevMessage("screenshots/registration_page.png", admission_2, "1 Первым шагом нужно зарегестрироваться на сайте электронной приемной комиссии СПБПУ "));
                sendText(id, "2. Теперь нужно подтвердить регистрацию в письме, которое должно прийти к вам на почту");
                sendPhoto(admission_3, id.toString(), "screenshots/confirmation.png");
                break;
            case "заполнениеАнкеты": {
                prevMessageStack.add(new PrevMessage("screenshots/confirmation.png", admission_3, "2. Теперь нужно подтвердить регистрацию в письме, которое должно прийти к вам на почту"));
                sendText(id, "3. Вас перенаправит на страницу сайта приемной комиссии с сообщением об успешной регистрации." +
                        "\n вам нужно нажать на зеленую кнопку \"войти\" и заполнить анкету абитуриента");
                sendPhoto(admission_4, id.toString(), "screenshots/form.png");
                break;
            }
            case "заявлениеНаПоступление": {
                prevMessageStack.add(new PrevMessage("screenshots/form.png", admission_4, "3. Вас перенаправит на страницу сайта приемной комиссии с сообщением об успешной регистрации."));
                sendText(id, "4. Теперь можно подать заявление на поступление");
                sendPhoto(admission_5, id.toString(), "screenshots/statement.png");
                break;
            }
            case "Образовательные программы": {
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> Поступление </b>"));
                sendMenu(id, "1 - Образовательные программы по направлениям ипмэита ( с упоминанием профильных дисциплин" +
                        "\n 2 - Подробно об образовательных программах и учебных планах по всем направлениям ", educationalPrograms);
                break;
            }

            case "Общие вопросы":
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> Поступление </b>"));
                sendMenu(id, generalQuestions, keyboardGenQuest);
                break;
            case "1": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendText(id,"Максимальное количество направлений, на которые абитуриент может подать заявление в вуз, — 4.");
                break;
            }
            case "2": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"Проходные баллы текущего года становятся известны только после того, как все абитуриенты " +
                        "зачисляются и составляется статистика. Поэтому известны только проходные баллы прошлых лет, и, по сути," +
                        "они ничего не значат.",genQuest_1);
                break;
            }
            case "3": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"Здесь можно найти дату публикации конкурсных списков",genQuest_34);
                break;
            }
            case "4": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"Здесь можно найти дату публикации приказов о зачислении",genQuest_34);
                break;
            }
            case "5": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"\uD83D\uDD17При поступлении на какие специальности необходим медицинский осмотр",genQuest_5);
                break;
            }
            case "6": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"\uD83D\uDD17 Количество мест на … направление подготовки",genQuest_6);
                break;
            }
            case "7": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendMenu(id,"\uD83D\uDD17Учет индивидуальных достижений",genQuest_7);
                break;
            }
            case "8": {
                prevMessageStack.add(new PrevMessage(keyboardGenQuest, generalQuestions));
                sendText(id,"Адрес приемной комиссии ИПМЭиТ: \n" +
                        "ул.Политехническая, д. 29, 3-й учебный корпус, ауд. 104.\n" +
                        "Режим работы: понедельник – пятница с 10.00 до 16.00 (обед с 13.00 до 14.00)\n" +
                        "\n" +
                        "Контактный телефон: +79219005542\n" +
                        "Почта: abit.imet@spbstu.ru"
                );
                break;
            }
            case "По ЕГЭ":
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> Поступление </b>"));
                sendMenu(id,"По ЕГЭ",examKeyboard);
                break;
            case "По вступительным испытаниям":
                prevMessageStack.add(new PrevMessage(keyboardEntrance, "<b> Поступление </b>"));
                sendMenu(id,"По вступительным испытаниям",entranceTestKeyboard);
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
                        sendText(id, "Привет, дорогой абитуриент, я цифровой советник, разработанный " +
                                "студентами СПБПУ специально для тебя. Я создан, чтобы давать ответы на популярные вопросы.\n Так что " +
                                "выбери тему, которая тебя интересует -> ");
                        sendMenu(id, "<b>Выбери тему:</b>", keyboardHello, mainTopics);
                    }
                }
                return;
            } else if (msg.hasText()) {
                switch (msg.getText()) {
                    case "Поступление\uD83D\uDE0B" -> {
                        sendMenu(id, "<b> Поступление </b>", keyboardEntrance);
                    }
                    case "Полезные ссылки\uD83D\uDD17" -> {
                        sendMenu(id, "Полезные ссылки:", linksKeyboard);
                    }
                    case "Общежитие\uD83C\uDFE0" -> {
                        sendMenu(id,"Информация об общежитиях",dormKeyboard);
                    }
                    case "Стипендии\uD83D\uDCB5" -> {
                        sendMenu(id,"Стипендии", scholarshipKeyboard);
                    }
                    case "Платное обучение\uD83D\uDCC3" -> {
                        sendMenu(id,"Стоимость обучения в семестр на ... направление подготовки (таблица) ",costKeyboard);
                    }
                    case "Индивидуальные достижения\uD83C\uDFC6" -> {
                        sendMenu(id,"Учет индивидуальных достижений",achievementsKeyboard);
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
