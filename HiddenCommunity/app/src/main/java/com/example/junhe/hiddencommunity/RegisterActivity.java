package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private ImageView img_Number;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etNickname;
    private EditText etMajor;
    private Button bMajorAdd;
    private Button bStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        etNickname = (EditText) findViewById(R.id.etNickname);
        etMajor = (EditText) findViewById(R.id.etMajor);
        bMajorAdd = (Button) findViewById(R.id.bMajorAdd);
        bStart = (Button) findViewById(R.id.bStart);

        // 비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if( password.equals(confirm) ) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNickname.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //'k2df456'라는 텍스트 값 넣어놨으나, 랜덤 닉네임 주어지게 바꿔야 함!!☆☆☆
                etNickname.setText("");
//              etNickname.requestFocus();
                return;
            }
        });

        etMajor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MajorListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });


        bMajorAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 전공 입력 칸 생성☆☆☆☆☆☆☆☆
            }
        });

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 비밀번호 입력 확인
                if( etPassword.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if( etPasswordConfirm.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if( !etPassword.getText().toString().equals(etPasswordConfirm.getText().toString()) ) {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    //etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPasswordConfirm.requestFocus();
                    return;
                }

                // 닉네임 입력 확인
                if( etNickname.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
                    etNickname.requestFocus();
                    return;
                }

                //닉네임 중복 확인 ---> 서버 상의 데이터로 중복 확인 가능??☆☆☆☆☆☆☆☆☆☆☆☆
                //if( etNickname.getText())

                // 전공 입력 확인
                if( etMajor.getText().toString().length() == 0 ) {
                    Toast.makeText(RegisterActivity.this, "전공을 입력하세요", Toast.LENGTH_SHORT).show();
                    etMajor.requestFocus();
                    return;
                }

            /*    Intent intent = new Intent(getApplicationContext(), MajorActivity.class);
                 startActivityForResult(intent, 1000);*/

//                Intent result = new Intent();
//                result.putExtra("Nickname", etNickname.getText().toString());

                //               // 자신을 호출한 액티비티로 데이터를 보낸다.
                //               setResult(RESULT_OK, result);
                //               finish();

//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });



            }
        });
    }
}
