package com.ce.nw.auth.support.properities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.Serializable;

/**
 * @author yuit
 * @date 2018/10/19 16:30
 *
 */
public class BootLogLevelProperties implements Serializable {

    private String level = "INFO";

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
