package com.example.forum.controller.form;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter

public class CommentForm {

    private int id;
    private String text;
    private int message_id;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

}
