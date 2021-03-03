package com.keith.test.boottest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 价格
 *
 * @author mick
 * @since 2016/7/22 10:31
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Price implements Serializable, Comparable<Price> {

    private static final long serialVersionUID = 6023821195748092581L;
    /**
     * 价格(单位：分)
     */
    private long priceCent;
    /**
     * 价格(单位：元)
     */
    private double price;

    @Override
    public int compareTo(Price price) {
        if (price == null) {
            return 1;
        }
        if (this.priceCent > price.priceCent) {
            return 1;
        } else if (this.priceCent == price.priceCent) {
            return 0;
        } else {
            return -1;
        }
    }

}

