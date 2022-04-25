package com.sk.interview.c3.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Meta {
    private String id;
    private UUID uuid;
    private String[] stems;
    private String[][] syns;
    private String[][] ants;
    private boolean offensive;
}