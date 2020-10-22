package com.richstonedt.fcjx.dsp.paas.tag.schedule.impl;

import com.richstonedt.fcjx.dsp.paas.tag.schedule.IPaasTagFileScheduledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class PaasTagFileScheduledServiceDynamicCron implements SchedulingConfigurer {

    @Autowired
    ConfigurableApplicationContext applicationContext;

    @Autowired
    private IPaasTagFileScheduledService paasTagFileScheduledService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(() -> paasTagFileScheduledService.scheduled(), triggerContext -> {
            String cron = applicationContext.getEnvironment().getProperty("paas.tag.ftp.scheduled-cron");
            log.info("paas.tag.ftp.scheduled-cron:{}", cron);
            return new CronTrigger( Objects.requireNonNull(cron) ).nextExecutionTime(triggerContext);
        });
    }
}
