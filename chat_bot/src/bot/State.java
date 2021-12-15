package bot;

public enum State {
    WAIT_VALUE_IN_RUBLES,
    WAIT_NAME_OF_CURRENCY,
    WAIT_ANSWER_TO_QUESTION,
    NEED_TO_SEND_POST,
    DEFAULT,
}
