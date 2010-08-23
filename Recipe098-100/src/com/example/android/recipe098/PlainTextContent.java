package com.example.android.recipe098;

import java.io.IOException;
import java.io.OutputStream;

import com.google.api.client.http.HttpContent;

public class PlainTextContent implements HttpContent {

    private String mText = "";

    public PlainTextContent(String text) {
        mText = text;
    }

    public String getEncoding() {
        return null;
    }

    public long getLength() {
        return mText.getBytes().length;
    }

    public String getType() {
        return "text/plain";
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(mText.getBytes());
    }
}
