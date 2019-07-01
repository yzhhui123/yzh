package com.qf.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Email implements Serializable {
    private String from;
    private String to;
    private String subject;
    private String text;
    private Date sentDate;
}
