package com.cindyjick.res.assist.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Params {
    public static final String CONTEXT = "Context";
    private final Map<String, Class<?>> param;
    private final Map<String, LinkedList<String>> source;

    public Params(List<String> varCodeList) {
        this.param = new ConcurrentHashMap<>();
        this.source = new ConcurrentHashMap<>();
        Optional.ofNullable(varCodeList).ifPresent(e -> e.forEach(f -> {
            param.put(f, String.class);
            source.computeIfAbsent(f, k -> new LinkedList<>()).add(CONTEXT);
        }));
    }
}
