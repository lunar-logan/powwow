package io.powwow.core.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NotifierFactoryImpl implements NotifierFactory {
    private final Map<String, Notifier> notifierMap;

    @Autowired
    public NotifierFactoryImpl(List<Notifier> notifiers) {
        Map<String, Notifier> map = new HashMap<>();
        notifiers.forEach(notifier -> notifier.schemes().forEach(scheme -> map.put(scheme, notifier)));
        notifierMap = Collections.unmodifiableMap(map);
    }

    @Override
    public Optional<Notifier> getNotifierByScheme(String scheme) {
        return Optional.ofNullable(notifierMap.get(scheme));
    }
}
