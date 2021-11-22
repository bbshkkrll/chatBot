package bot.database;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.Array;

public class PostDB {
    public Post[] posts = new Post[]{
            new Post("На 1 доллар вы сможете купить 1 кусок пиццы в Нью-Йорке",
                    "В большинстве заведений Нью-Йорка пицца будет стоить вам небольшого состояния. Но зачем покупать целую пиццу, когда можно приобрести кусочек? В 2 Bros Pizza или 99¢ Fresh Pizza вам продадут его всего за 99 центов!", 0.8, 1.5, "USD"),
    };
}


