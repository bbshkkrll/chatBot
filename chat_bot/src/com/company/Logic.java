package com.company;

public class Logic {

    CommandsRepository commands = new CommandsRepository();
    RequestParser parser = new RequestParser();

    public String handleUserInput(String userInput, User user) {
        if (user.currentState == User.State.Default) {
            switch (userInput) {
                case "/start":
                    return "Привет! Я знаю актуальные курсы популярных валют и умею конвертировать сумму!\nЧтобы узнать больше напиши /help.";
                case "/help":
                    return commands.help.reference;
                case "/exit":
                    return commands.exit.reference;
                case "/rate":
                    user.nextState(User.State.SetValue);
                    return "Введите сумму в рублях (для разделения целой и дробной частей используйте точку)";
                case "/currencies":
                    return "Доступен актуальный курс данных валют:\nUSD, EUR, CNY, CAD, UAH, CZK, JPY";
                default:
                    return "Я не знаю как на это ответить";
            }
        } else if (user.currentState == User.State.SetValue) {
            try {
                var value = Double.parseDouble(userInput);
                if  (value <= 0) {
                    user.nextState(User.State.Default);
                    return "Некорректный ввод :(";
                }
                user.value = value;
                System.out.println(user.value);
            } catch (NumberFormatException e) {
                user.nextState(User.State.Default);
                System.out.println(e.getMessage());
                return "Некоректный ввод :(";
            }
            user.nextState(User.State.GetRate);
            return "Введите международный код валюты, например USD";
        } else if (user.currentState == User.State.GetRate) {
            user.nextState(User.State.Default);
            return switch (userInput.toLowerCase()) {
                case "usd" -> String.format("За %.2f₽ дают %.2f$", user.value, Double.parseDouble(parser.getRequest().rates.USD) * user.value);
                case "eur" -> String.format("За %.2f₽ дают %.2f€", user.value, Double.parseDouble(parser.getRequest().rates.EUR) * user.value);
                case "cny" -> String.format("За %.2f₽ дают %.2f¥", user.value, Double.parseDouble(parser.getRequest().rates.CNY) * user.value);
                case "cad" -> String.format("За %.2f₽ дают %.2fC$", user.value, Double.parseDouble(parser.getRequest().rates.CAD) * user.value);
                case "uah" -> String.format("За %.2f₽ дают %.2f₴", user.value, Double.parseDouble(parser.getRequest().rates.UAH) * user.value);
                case "czk" -> String.format("За %.2f₽ дают %.2fKč", user.value, Double.parseDouble(parser.getRequest().rates.CZK) * user.value);
                case "jpy" -> String.format("За %.2f₽ дают %.2fKč", user.value, Double.parseDouble(parser.getRequest().rates.JPY) * user.value);
                default -> "Таких валют не знаем";
            };
        } else return "Ушёл в никуда";
    }
}
