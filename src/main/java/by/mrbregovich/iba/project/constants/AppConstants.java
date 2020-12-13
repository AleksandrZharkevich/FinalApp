package by.mrbregovich.iba.project.constants;

public final class AppConstants {

    //Exceptions
    public static final String LOGIN_ALREADY_REGISTERED_MSG = "Пользователь с таким логином уже зарегистрирован";
    public static final String PHONE_NUMBER_ALREADY_REGISTERED_MSG = "Пользователь с таким номером телефона уже зарегистрирован";
    public static final String LOGIN_NOT_FOUND_MSG = "Пользователь с таким логином не найдет";
    public static final String USER_ID_NOT_FOUND_MSG = "Пользователь с таким id не найден";
    public static final String COMPANY_ID_NOT_FOUND_MSG = "Компания с таким id не найдена";
    public static final String REQUEST_WITH_PHONE_ALREADY_REGISTERED = "Заявка с таким номером телефона уже зарегистрирована";
    public static final String REQUEST_ID_NOT_FOUND_MSG = "Заявка с таким id не найдена";

    //ValidationMessages
    public static final String VALIDATION_LOGIN = "Введите логин";
    public static final String VALIDATION_PASSWORD = "Введите пароль";
    public static final String VALIDATION_FIRST_NAME = "Введите имя";
    public static final String VALIDATION_LAST_NAME = "Введите фамилию";
    public static final String VALIDATION_CORRECT_EMAIL = "Введите корректный имейл";
    public static final String VALIDATION_EMAIL = "Введите имейл";
    public static final String VALIDATION_REGION = "Введите область";
    public static final String VALIDATION_DISTRICT = "Введите район";
    public static final String VALIDATION_CITY = "Введите название населенного пункта";
    public static final String VALIDATION_STREET_ADDRESS = "Введите улицу и номер дома";
    public static final String VALIDATION_PHONE_NUMBER = "Введите номер телефона в формате +375(XX)XXXXXXX";
    public static final String VALIDATION_DURATION = "Введите продолжительность компании в днях";
    public static final String VALIDATION_DESCRIPTION = "Введите краткое описание компании";
    public static final String VALIDATION_COMPANY_NAME = "Введите название компании";

    public static final int COMPANIES_PAGE_SIZE = 5;
    public static final int REQUESTS_PAGE_SIZE = 2;
    public static final String NO_IMG_SRC = "/images/no_photo.png";
    public static final String AMOUNT_MUST_BE_POSITIVE = "Неверно указана сумма";
    public static final String THANKS_FOR_DONATE = "Спасибо за поддержку компании";
    public static final String FAIL_JOIN_COMPANY_MSG_SHOULD_LOG_IN = "Ввойдите или зарегистрируйтесь";
    public static final String FAIL_QUIT_COMPANY_MSG_SHOULD_LOG_IN = "Ввойдите или зарегистрируйтесь";
    public static final String OK_JOIN_COMPANY_MSG = "Спасибо, что присоединились к компании";
    public static final String OK_QUIT_COMPANY_MSG = "Вы покинули компанию";
    public static final String PARTICIPANT_ALREADY_JOINED = "Вы уже зарегистрированы в компании";
    public static final String PARTICIPANT_IS_NOT_JOINED = "Вы не зарегистрированы в компании";
    public static final String USER_IS_NOT_PARTICIPANT = "Присоединитесь к проекту, чтобы обрабатывать заявки";
    public static final String CHECK_LOGIN_PASS_MSG = "Проверьте логин и пароль";
    public static final String VALIDATION_MAIL_CONTENT = "Введите сообщение";
    public static final String VALIDATION_MAIL_SUBJECT = "Введите тему сообщения";
    public static final String DEV_EMAIL = "zharkevich1.19@gmail.com";
}
