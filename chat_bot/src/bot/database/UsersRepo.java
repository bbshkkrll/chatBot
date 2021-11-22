package bot.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

public class UsersRepo {
    private MongoCollection<User> users;

    public UsersRepo() {
        var codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .register(
                                ClassModel.builder(User.class).enableDiscriminator(true).build()
                        ).automatic(true)
                        .build()));

        users = MongoClients.create(System.getenv("MONGO_URI")).getDatabase("bot_data")
                .withCodecRegistry(codecRegistry)
                .getCollection("bot_users", User.class);
    }

    public User getUserOrCreate(long chatId, String userName) {
        var user = users.find(Filters.eq("chatID", chatId)).first();
        return user == null ? new User(chatId, userName) : user;
    }

    public void put(User user) {
        users.updateOne(Filters.eq("chatID", user.chatID), new Document("$set", user),
                new UpdateOptions().upsert(true));
    }
}
