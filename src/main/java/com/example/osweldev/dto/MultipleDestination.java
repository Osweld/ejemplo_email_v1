package com.example.osweldev.dto;

import lombok.Data;

@Data
public class MultipleDestination {

    private String[] to;
    private String subject;
    private String body;
}
