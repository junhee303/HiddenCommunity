package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etNickname;
    private EditText etMajor1;
    private EditText etMajor2;
    private EditText etMajor3;
    private Button bMajorAdd;
    private Button bStart;
    private TextView CheckPasswordNotice;
    private TextView CheckNicknameNotice;
    private ImageView passwordCheck;
    private ImageView passwordError;
    private ImageView nicknameCheck;
    private ImageView nicknameError;
    private String server_nickname;
    private int count_major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        etNickname = (EditText) findViewById(R.id.etNickname);
        etMajor1 = (EditText) findViewById(R.id.etMajor1);
        etMajor2 = (EditText) findViewById(R.id.etMajor2);
        etMajor3 = (EditText) findViewById(R.id.etMajor3);
        bMajorAdd = (Button) findViewById(R.id.bMajorAdd);
        bStart = (Button) findViewById(R.id.bStart);
        CheckPasswordNotice = (TextView) findViewById(R.id.CheckPasswordNotice);
        CheckNicknameNotice = (TextView) findViewById(R.id.CheckNicknameNotice);
        passwordCheck = (ImageView) findViewById(R.id.passwordCheck);
        passwordError = (ImageView) findViewById(R.id.passwordError);
        nicknameCheck = (ImageView) findViewById(R.id.nicknameCheck);
        nicknameError = (ImageView) findViewById(R.id.nicknameError);
        server_nickname = "server"; // 닉네임 중복 체크 위해 임의로 만든 닉네임
        count_major = 1;

        conformPassword(); // 비밀번호 일치 검사
        conformNickname(); // 닉네임 중복 검사
        addMajor(); // [+] 버튼 클릭 시 전공 최대 3개 등록 가능
        selectMajor(); // 전공 입력 부분 클릭 시 MajorActivity로 넘어감
        pushStartButton(); // 정보 입력 유무 확인 및 가입 완료

    }

    public void conformPassword() {
        // 비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if (password.equals(confirm)) {
                    passwordCheck.setVisibility(View.VISIBLE);
                    passwordError.setVisibility(View.INVISIBLE);
                    CheckPasswordNotice.setVisibility(View.VISIBLE);
                } else {
                    passwordCheck.setVisibility(View.INVISIBLE);
                    passwordError.setVisibility(View.VISIBLE);
                    CheckPasswordNotice.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void conformNickname() {
        // 닉네임 중복 검사
        etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nickname = etNickname.getText().toString();
                String serverName = server_nickname;

                if (nickname.equals(serverName) || nickname.length() == 0) {
                    nicknameCheck.setVisibility(View.INVISIBLE);
                    nicknameError.setVisibility(View.VISIBLE);
                    CheckNicknameNotice.setVisibility(View.INVISIBLE);
                } else {
                    nicknameCheck.setVisibility(View.VISIBLE);
                    nicknameError.setVisibility(View.INVISIBLE);
                    CheckNicknameNotice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

//        etNickname.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //'k2df456'라는 텍스트 값 넣어놨으나, 랜덤 닉네임 주어지게 바꿔야 함!!☆☆☆
//                etNickname.setText("");
//                etNickname.requestFocus();
//                return;
//            }
//        });

    public void selectMajor() {
        etMajor1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MajorListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

    public void addMajor() {
        bMajorAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 전공 입력 칸 추가 생성☆☆☆☆☆☆☆☆
                count_major++;
                if (count_major >= 2) {
                    etMajor2.setVisibility(View.VISIBLE);
                }
                if (count_major >= 3) {
                    etMajor3.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void pushStartButton() {
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 비밀번호 입력 확인
                if (etPassword.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if (etPasswordConfirm.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if (!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    //etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPasswordConfirm.requestFocus();
                    return;
                }

                // 닉네임 입력 확인
                if (etNickname.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
                    etNickname.requestFocus();
                    return;
                }

                //닉네임 중복 확인 ---> 서버 상의 데이터로 중복 확인 가능??☆☆☆☆☆☆☆☆☆☆☆☆
                if (etNickname.getText().toString().equals(server_nickname)) {
                    Toast.makeText(RegisterActivity.this, "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    //etPassword.setText("");
                    etNickname.setText("");
                    etNickname.requestFocus();
                    return;
                }

                // 전공 입력 확인
                if (etMajor1.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "전공을 입력하세요", Toast.LENGTH_SHORT).show();
                    etMajor1.requestFocus();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), BoardWritingActivity.class);
                startActivityForResult(intent, 1000);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        etMajor1 = (EditText) findViewById(R.id.etMajor1);
        if (resultCode == RESULT_OK) {// 액티비티가 정상적으로 종료되었을 경우
            if (requestCode == 1000) {// MajorListActivity에서 호출한 경우에만 처리
                // 받아온 전공명을 MajorListActivity에 표시
                etMajor1.setText(data.getStringExtra("selected_major"));
            }
        }
    }
}

