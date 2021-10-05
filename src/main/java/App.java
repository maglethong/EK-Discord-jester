import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

/**
 * Dummy Main class
 *
 * @author Maglethong spirr
 * @since 30/09/2021
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws LoginException, InterruptedException, IOException {
        LOGGER.info("Application Started");

        final InputStream stream = new FileInputStream("_Local_/application.properties");
        final Properties props = new Properties();
        props.load(stream);

        final String token = props.getProperty("app.bot.token");

        final JDA jda = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES)
                                  .addEventListeners(new ExampleListener())
                                  .addEventListeners(new PingListener())
                                  .addEventListeners(new DiceParser(new Random()))
                                  .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
                                  .build();

        jda.awaitReady();

        LOGGER.trace("Invite URL: " + jda.getInviteUrl());
    }
}
