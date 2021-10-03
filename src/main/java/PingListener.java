import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Example {@link ListenerAdapter}
 *
 * @author Maglethong spirr
 * @since 03/10/2021
 */
public class PingListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!")
                   .queue(response -> response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time)
                                              .queue());
        }
    }
}
