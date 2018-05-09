package com.zharker.chapter8.command;

public class Close implements Action {

    private final Editor editor;

    public Close(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.close();
    }

//    @Override
    public void hehe() {
        System.out.println("close hehe");
    }

}
