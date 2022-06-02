package com.course.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class EventBusSpringImp implements ApplicationContextAware,EventBus {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext EventBus) throws BeansException {
        this.applicationContext = EventBus;
    }

    @Override
    public void publishEvent(Object Event) {
       applicationContext.publishEvent(Event);
    }
}
