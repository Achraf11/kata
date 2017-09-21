package com.sg.kata.account.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Amount entity
 * 
 * @author Charaf
 *
 */
@Value
@Builder
@NoArgsConstructor(force = true)
public class Amount {
    private long value;

    public Amount(long value) {
        this.value = validate(value);
    }

    private long validate(long value) {
        if(value > 0) {
            return value;
        }
        throw new IllegalArgumentException("Amount should be positif");
    }
}
