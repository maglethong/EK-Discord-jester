import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

/**
 * Example {@link EventListener}
 *
 * @author Maglethong spirr
 * @since 03/10/2021
 */
public class ExampleListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            System.out.println("API is ready!");
        } else if (event instanceof MessageReceivedEvent) {
            final MessageReceivedEvent ev = (MessageReceivedEvent) event;
            final Message msg = ev.getMessage();
            if (msg.getContentRaw().equals("Hello"))
            {
                MessageChannel channel = ev.getChannel();
                channel.sendMessage("Goodbye")
                       .queue();
            }
        }
    }
}
