package com.example.one.feezhomeful;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by one on 2/09/2017.
 */

public class CommentFragment extends Fragment {
    private View vMain;
    private EditText edtName,edtEmail,edtContent;
    static Pattern emailPattern = Pattern.compile("[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");
    private String name;
    private String email;
    private String content;
    private Boolean nameCanBeSent,emailCanBeSent,contentCanBeSent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_comment, container, false);
        Button sendBtn = (Button) vMain.findViewById(R.id.btn_sendemail);
         edtName = (EditText)vMain.findViewById(R.id.editText_your_name);
         edtEmail = (EditText)vMain.findViewById(R.id.editText_your_email);
         edtContent = (EditText)vMain.findViewById(R.id.edt_order_note_text);

        name = edtName.getText().toString();
        email = edtName.getText().toString();
        content = edtContent.getText().toString();
        emailCanBeSent = false;
                nameCanBeSent = false;
                contentCanBeSent = false;
        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(edtName.getText().toString().isEmpty()){
                    edtName.setError("Please type in your Name");
                    nameCanBeSent = false;
                }else{
                    nameCanBeSent = true;
                }
            }
        });

        edtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(edtContent.getText().toString().trim() == ""){
                    edtContent.setError("Please write your suggestion");
                    contentCanBeSent = false;
                }else{
                    contentCanBeSent = true;
                }
            }
        });

        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(edtEmail.getText().toString().isEmpty()||!isValidEmail(edtEmail.getText().toString()) ){
                    edtEmail.setError("Please type in valid Email");
                    emailCanBeSent = false;
                }else{
                    emailCanBeSent = true;
                }
            }
        });



sendBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (nameCanBeSent && contentCanBeSent && emailCanBeSent) {
            Uri uri = Uri.parse("mailto:Feez.homfulMelbourne@gmail.com");
            String[] email = {"Feez.homfulMelbourne@gmail.com"};
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
            intent.putExtra(Intent.EXTRA_SUBJECT, "User Name: " + edtName.getText().toString() + " || User Email: " + edtEmail.getText().toString() + "."); // 主题
            intent.putExtra(Intent.EXTRA_TEXT, edtContent.getText().toString()); // 正文
            startActivity(Intent.createChooser(intent, "Please Choose one Mail application"));
        }
    else{
            if(!contentCanBeSent) {
                edtContent.setError("Please write your suggestion");
            }if(!emailCanBeSent) {
                edtEmail.setError("Please type in valid Email");
            }if(!nameCanBeSent) {
                edtName.setError("Please type in your Name");
            }
//        Toast.makeText(getContext(),"Please type in correct info",Toast.LENGTH_SHORT).show();
    }
}});


        return vMain;

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

//    public static boolean isValidEmail(String email)
//    {
//        Matcher m = emailPattern.matcher(email);
//        return !m.matches();
//    }


}
