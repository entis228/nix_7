package com.entis.entity;

public record Route(
        int id,
        int from_id,
        int to_id,
        int cost
) { }
