package org.example.model;

import org.apache.commons.lang3.StringUtils;

public record User(Long id, String login, String password) {
    // 1230000000
    // 0000000123
    String getIdZeroPadded() {
        return StringUtils.leftPad(id.toString(), 10, '0');
    }
}