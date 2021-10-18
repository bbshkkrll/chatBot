package com.company;

public class SingerRepository {
    public Singer[] singers = new Singer[]{
            new Singer("Михаил Круг", "1.Владимирский централ \n2.Золотые купола \n3.Жиган лимон"),
            new Singer("Михаил Шуфутинский", "1.3-е сентября \n2.Марджанджа \n3.Еврейский портной"),
            new Singer("Александр Розенбаум", "1.Гоп-Стоп \n2.Заходите к нам на огонек \n3.Песня врача скорой помощи"),
            new Singer("Бутырка", "1.Аттестат \n2.Шарик \n3.А для вас я никто"),
            new Singer("Лесоповал", "1.Был пацан и нет пацана \n2.Мне улыбнись \n3.Осенний дождь"),
            new Singer("Воровайки", "1.Шмон \n2.Хоп мусорок \n3.Мурки воровайки"),
    };

    public String getSinger(String name) {
        for (Singer singer : singers)
            if (name.equals(singer.name.toLowerCase()))
                return singer.songs;
        return "Я такэ не бачу";

    }

}
