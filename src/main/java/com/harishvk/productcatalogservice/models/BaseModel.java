package com.harishvk.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseModel {
    int id;
    int createdAt;
    int lastModifiedAt;
    Status status;
}
