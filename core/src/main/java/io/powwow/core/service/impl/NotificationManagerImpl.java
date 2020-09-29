package io.powwow.core.service.impl;

import io.powwow.core.model.Subscriber;
import io.powwow.core.model.Transition;
import io.powwow.core.model.execution.ExecutionContext;
import io.powwow.core.notification.ImmutableNotification;
import io.powwow.core.notification.Notification;
import io.powwow.core.notification.NotificationService;
import io.powwow.core.service.NotificationManager;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class NotificationManagerImpl implements NotificationManager {
    private final NotificationService notificationService;

    public NotificationManagerImpl(NotificationService notificationService) {
        this.notificationService = Objects.requireNonNull(notificationService);
    }

    @Override
    public void send(ExecutionContext executionContext, Transition transition) {
        getEligibleSubscribers(executionContext.getStateMachineDef().getSubscribers(), transition)
                .forEach(subscriber -> notifySubscriber(subscriber, executionContext.getCorrelationId(), transition.getToState()));

    }

    private void notifySubscriber(Subscriber subscriber, String correlationId, String toState) {
        final Notification notification = ImmutableNotification.builder()
                .id(subscriber.getName() + ":" + correlationId)
                .body(buildNotificationBody(correlationId, toState))
                .connectionUri(URI.create(subscriber.getCallbackUrl()))
                .connectionProperties(subscriber.getProperties())
                .build();
        notificationService.send(notification);
    }

    private Map<String, String> buildNotificationBody(String correlationId, String toState) {
        return Map.of("correlationId", correlationId, "state", toState);
    }

    private List<Subscriber> getEligibleSubscribers(List<Subscriber> subscribers, Transition transition) {
        return subscribers.stream()
                .filter(isEligible(transition))
                .collect(Collectors.toList());
    }

    private Predicate<? super Subscriber> isEligible(Transition transition) {
        return subscriber -> subscriber.getState().equals(transition.getToState());
    }
}
