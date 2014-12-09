package com.site.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangjie on 2014/12/8.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 2302038108825536119L;

    /**实体主键*/
    @Transient
    public abstract Long getId();
    /**实体主键*/
    @Transient
    public abstract void setId(Long id);

}
