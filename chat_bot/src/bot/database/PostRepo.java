package bot.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.*;

import static com.mongodb.client.model.Filters.*;

public class PostRepo {

    private MongoCollection<Post> posts;

    public PostRepo() {
        var classModel = ClassModel.builder(Post.class);
        var codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .register(
                                classModel.conventions(Arrays.asList(Conventions.SET_PRIVATE_FIELDS_CONVENTION)).build()
                        ).automatic(true)
                        .build()));

        posts = MongoClients.create(System.getenv("MONGO_URI")).getDatabase("bot_data")
                .withCodecRegistry(codecRegistry)
                .getCollection("posts", Post.class);

    }

    public Post getPostByValue(String currency, double userValue) {
        var filter = and(lte("minValue", userValue), gte("maxValue", userValue),
                eq("currency", currency.toUpperCase()));
        var post = getRandomPost(posts.find(filter));
        return post == null ? new Post("Данных пока нет") : post;
    }

//    public void initializePosts() {
//        var posts1 = new PostDB();
//        posts.insertMany(Arrays.asList(posts1.posts));
//    }

    public Post getRandomPost(FindIterable<Post> posts) {
        var iterator = posts.iterator();
        var list = new ArrayList<Post>();

        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list.isEmpty() ? null : list.get(new Random().nextInt(list.size()));
    }
}
