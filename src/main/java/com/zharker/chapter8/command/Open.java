package com.zharker.chapter8.command;

// BEGIN Open
public class Open implements Action {

    private final Editor editor;

    public Open(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.open();
    }

//    @Override
    public void hehe() {
        System.out.println("open hehe");
    }
}
// END Open
