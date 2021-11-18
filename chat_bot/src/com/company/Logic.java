package com.company;

public class Logic {

    RequestParser parser = new RequestParser();

    public String handleUserInput(String userInput, User user) {
        if (user.getCurrentState() == State.WAIT_VALUE_IN_RUBBLES) {
            try {
                var value = Double.parseDouble(userInput);
                if (value <= 0) {
                    user.setNextState(State.DEFAULT);
                    return "Некорректный ввод";
                }
                user.value = value;
                System.out.println(user.value);
            } catch (NumberFormatException e) {
                user.setNextState(State.DEFAULT);
                return "Некоректный ввод";
            }
            user.setNextState(State.WAIT_NAME_OF_CURRENCY);
            return "Введите международный код валюты, например USD";
        } else if (user.getCurrentState() == State.WAIT_NAME_OF_CURRENCY) {
            user.setNextState(State.DEFAULT);
            var rates = parser.getRequest();
            if (rates.containsKey(userInput.toUpperCase()))
                return String.format("За %.2f₽ вы получите %.2f %s", user.value,
                        rates.get(userInput.toUpperCase()) * user.value, userInput.toUpperCase());
            else return "Я не знаю курс данной валюты";
        }
        switch (userInput) {
            case "/start":
                return "Привет! Я знаю актуальные курсы популярных валют и умею конвертировать сумму!\nЧтобы узнать больше напиши /help.";
            case "/help":
                return "Бот умеет конвертировать рубли во все популярные валюты. \nЧтобы посмотреть все доступные валюты напиши /currencies.";
            case "/exit":
                return "Эта функция пока недоступна. Мы (я) работаем ((работаю)) над этим.";
            case "/rate":
                user.setNextState(State.WAIT_VALUE_IN_RUBBLES);
                return "Введите сумму в рублях (для разделения целой и дробной частей используйте точку)";
            case "/currencies":
                return "Доступен актуальный курс данных валют:\nUSD, EUR, CNY, CAD, UAH, CZK, JPY";
            default:
                return "Я не знаю как на это ответить.";
        }
    }
}
