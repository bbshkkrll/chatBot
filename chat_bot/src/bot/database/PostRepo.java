package bot.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;

public class PostRepo {

    private MongoCollection<Post> posts;

    public PostRepo() {
        var codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .register(
                                ClassModel.builder(Post.class).enableDiscriminator(true).build()
                        ).automatic(true)
                        .build()));

        posts = MongoClients.create(System.getenv("MONGO_URI")).getDatabase("bot_data")
                .withCodecRegistry(codecRegistry)
                .getCollection("posts", Post.class);

    }

    public Post getPostByValue(double userValue) {
        var post = posts.find(Filters.and(Filters.gte("minValue", userValue),
                Filters.lte("maxValue", userValue))).first();
        return post == null ? new Post("Такого факта еще нет", "Работаем над этим") : post;
    }

    public void initializePosts(){
        var posts1 = new PostDB();
        posts.insertMany(Arrays.asList(posts1.posts));
    }
}
