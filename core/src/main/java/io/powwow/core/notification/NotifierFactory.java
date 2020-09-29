package io.powwow.core.notification;

import java.util.Optional;

public interface NotifierFactory {
    Optional<Notifier> getNotifierByScheme(String scheme);
}
