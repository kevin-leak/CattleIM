package com.example.netKit.piece;

public class FilePiece {

    /**
     * name : ksadjk
     * content : asdfjkal
     */

    private String name;
    private String content;

    public FilePiece(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
