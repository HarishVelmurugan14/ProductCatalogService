package com.harishvk.productcatalogservice.models;

import lombok.Getter;

@Getter
public class Status {
    public enum STATUS {
        ACTIVE,
        IN_ACTIVE
    }
}
