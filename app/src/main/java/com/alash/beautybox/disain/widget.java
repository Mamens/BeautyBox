package com.alash.beautybox.disain;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public class widget implements TextWatcher {

    EditText editText;
    int textlength;
    public widget(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        String text = editText.getText().toString();
        textlength = editText.getText().length();

        if (text.endsWith(" "))
            return;

        if (before == 0) {
            if (textlength == 1) {
                if (!text.contains("+7")) {
                    editText.setText("+7");
                    editText.setSelection(editText.getText().length());
                }
            } else if (textlength == 3) {
                if (!text.contains("(")) {
                    editText.setText(new StringBuilder(text).insert(text.length() - 1, "(").toString());
                    editText.setSelection(editText.getText().length());
                }

            } else if (textlength == 7) {

                if (!text.contains(")")) {
                    editText.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                    editText.setSelection(editText.getText().length());
                }

            } else if (textlength == 11 || textlength == 14) {
                editText.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                editText.setSelection(editText.getText().length());
            }

        } else {
            if (textlength == 2) {
                editText.setText("");
                editText.append("+7");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
