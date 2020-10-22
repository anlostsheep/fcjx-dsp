package com.richstonedt.fcjx.dsp.ticket.constant;

import lombok.Getter;

@Getter
public enum EnumRedisKey {

    PAAS_TAG_TICKET("PAAS:TAG:TICKET");

    private String key;

    EnumRedisKey(String key) {
        this.key = key;
    }

    public String getKey(String suffix) {
        return key + ":" + suffix;
    }
}
