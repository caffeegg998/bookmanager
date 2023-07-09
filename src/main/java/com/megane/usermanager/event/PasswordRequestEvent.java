package com.megane.usermanager.event;


import com.megane.usermanager.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PasswordRequestEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public PasswordRequestEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
