import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Dummy Main class
 *
 * @author Maglethong spirr
 * @since 30/09/2021
 */
public class App {
    public static void main(String[] args) throws LoginException, InterruptedException, IOException {
        final InputStream stream = new FileInputStream("_Local_/application.properties");
        final Properties props = new Properties();
        props.load(stream);

        final String token = props.getProperty("app.bot.token");

        final JDA jda = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES)
                            .addEventListeners(new ExampleListener())
                            .addEventListeners(new PingListener())
                            .build();

        jda.awaitReady();

        System.out.println(jda.getInviteUrl());
    }
}
