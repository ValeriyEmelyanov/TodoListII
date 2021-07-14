package com.example.todolistii.security;

import java.util.Calendar;
import java.util.Date;

public class TokenPayload {
    private Long userId;
    private String email;
    private Date created;

    public TokenPayload(Long userId, String email, Date created) {
        this.userId = userId;
        this.email = email;
        this.created = created;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
