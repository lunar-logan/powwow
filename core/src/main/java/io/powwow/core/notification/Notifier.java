package io.powwow.core.notification;

import java.util.List;

public interface Notifier {
    List<String> schemes();

    void send(Notification notification);
}
