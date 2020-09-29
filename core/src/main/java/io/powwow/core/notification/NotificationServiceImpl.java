package io.powwow.core.notification;

import io.powwow.core.observability.TimedResponse;
import io.powwow.core.observability.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final NotifierFactory notifierFactory;

    public NotificationServiceImpl(NotifierFactory notifierFactory) {
        this.notifierFactory = notifierFactory;
    }

    @Override
    public void send(Notification notification) {
        final Notifier notifier = getNotifier(notification);
        TimedResponse<Void> notifAction = Timer.timed(() -> {
            notifier.send(notification);
            return null;
        });
        if (notifAction.getValue().isSuccess()) {
            log.info("Notified subscriber: {} in {} ms", notification.getId(), notifAction.getExecutionTime().toMillis());
        } else {
            log.error("Notification to subscriber: {} failed due to ", notification.getId(), notifAction.getValue().getLeft());
        }
    }

    private Notifier getNotifier(Notification notification) {
        return notifierFactory.getNotifierByScheme(notification.getConnectionUri().getScheme()).orElseThrow(() -> new IllegalArgumentException("Unknown scheme: " + notification.getConnectionUri().getScheme()));
    }
}
