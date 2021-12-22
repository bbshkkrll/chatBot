package com;


public class Reply {
    private final String message;
    private final String[] buttons;

    public Reply(String[] buttons, String message) {
        this.message = message;
        this.buttons = buttons;
    }

    public String getMessage() {
        return message;
    }

    public String[] getButtons() {
        return buttons;
    }
}
