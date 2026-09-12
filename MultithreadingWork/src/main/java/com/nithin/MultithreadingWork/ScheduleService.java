package com.nithin.MultithreadingWork;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    private final TaskScheduler taskScheduler;

    public String scheduleTask(MailDto mailDto) {
        taskScheduler.schedule(() -> {
            log.info("Mail sent to {} with subject {} and content : {}",mailDto.getMailTo(),mailDto.getSubject(),mailDto.getContent());
        }, Instant.ofEpochSecond(4));
        return "Mail sent successfully";
    }
}
