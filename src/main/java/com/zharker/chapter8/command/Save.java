package com.zharker.chapter8.command;

// BEGIN Save
public class Save implements Action {

    private final Editor editor;

    public Save(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.save();
    }

//    @Override
    public void hehe() {
        System.out.println("save hehe");
    }
}
// END Save
