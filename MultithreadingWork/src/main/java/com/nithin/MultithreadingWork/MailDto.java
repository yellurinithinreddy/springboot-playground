package com.nithin.MultithreadingWork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {

    private String mailTo;
    private String subject;
    private String content;
}
