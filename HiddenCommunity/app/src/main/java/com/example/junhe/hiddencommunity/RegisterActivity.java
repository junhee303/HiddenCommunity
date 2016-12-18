package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etNickname;
    private EditText etMajor1;
    private EditText etMajor2;
    private EditText etMajor3;
    private Button bNicknameOverlapCheck;
    private Button bMajorAdd;
    private Button bStart;
    private TextView CheckPasswordNotice;
    private TextView CheckNicknameNotice;
    private ImageView passwordCheck;
    private ImageView passwordError;
    private ImageView nicknameCheck;
    private ImageView nicknameError;
    private String server_nickname;
    private int addMajor;
    private int count_major;

    String url_nickname, result_nickname;
    String url_register, result_register;

    private boolean nickname_overlap = false;


    //private ProgressDialog mProgressDialog;

    class NicknameOverlapTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result_nickname = HTTPInstance.Instance().Post(url_nickname);
                onResponseHttp_nickname(result_nickname);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    NicknameOverlapTask nicknameOverlapTask;

    private void onResponseHttp_nickname(String s) {
        Log.d("RESPONSE", s);
        if (s.compareTo("no") == 0) {
            System.out.println("중복된 닉네임입니다");
            //Toast.makeText(RegisterActivity.this, "중복된 닉네임입니다", Toast.LENGTH_SHORT).show();
        } else {
        //} else if (s.compareTo("ok") == 0) {
            System.out.println("사용 가능한 닉네임입니다");
            //Toast.makeText(RegisterActivity.this, "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
            nickname_overlap = true; // 닉네임 중복 체크 true
            System.out.println("nickname_overlap은 " + nickname_overlap);
        }
    }

    class RegisterTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result_register = HTTPInstance.Instance().Post(url_register);
                onResponseHttp_register(result_register);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    RegisterTask registerTask;

    private void onResponseHttp_register(String s) {
        if (s == null) {
            System.out.println("회원 정보를 정확히 입력해주세요");
            return;
        }
        Log.d("RESPONSE", s);
        if (s.compareTo("ok") == 0) {
            System.out.println("가입이 완료되었습니다");
            Intent intent = new Intent(getApplicationContext(), BoardRecyclerViewActivity.class);
            startActivityForResult(intent, 1000);
        }
    }

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
        bNicknameOverlapCheck = (Button) findViewById(R.id.bNicknameOverlapCheck);
        bMajorAdd = (Button) findViewById(R.id.bMajorAdd);
        bStart = (Button) findViewById(R.id.bStart);
        CheckPasswordNotice = (TextView) findViewById(R.id.CheckPasswordNotice);
        CheckNicknameNotice = (TextView) findViewById(R.id.CheckNicknameNotice);
        passwordCheck = (ImageView) findViewById(R.id.passwordCheck);
        passwordError = (ImageView) findViewById(R.id.passwordError);
        //server_nickname = "server"; // 닉네임 중복 체크 위해 임의로 만든 닉네임
        count_major = 1;

        conformPassword(); // 비밀번호 일치 검사
        conformNickname(); // 닉네임 중복 검사
        addMajor(); // [+] 버튼 클릭 시 전공 최대 3개 등록 가능
        selectMajor(); // 전공 입력 부분 클릭 시 MajorActivity로 넘어감
        pushStartButton(); // 정보 입력 유무 확인 및 가입 완료
    }

    // 비밀번호 일치 검사
    public void conformPassword() {
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

    // 닉네임 중복 검사
    public void conformNickname() {
        bNicknameOverlapCheck.setOnClickListener
                (new View.OnClickListener() {
                     public void onClick(View v) {
                         String nickname = etNickname.getText().toString();

                         if (etNickname.getText().toString().length() != 0) {
                             try {
                                 url_nickname = "http://52.78.207.133:3000/members/checkNickname/";
                                 url_nickname += URLEncoder.encode(nickname, "utf-8");
                             } catch (UnsupportedEncodingException e) {
                                 e.printStackTrace();
                             }
                         } else {
                             Toast.makeText(RegisterActivity.this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
                             etNickname.requestFocus();
                         }

                         nicknameOverlapTask = new NicknameOverlapTask();
                         nicknameOverlapTask.execute();

                         if(nickname_overlap == true) {
                             Toast.makeText(RegisterActivity.this, "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                             CheckNicknameNotice.setText("사용 가능한 닉네임입니다");
                         }

                     }

                 }
                );
    }

    // 전공 입력 칸 누르면 MajorListActivity로 이동
    public void selectMajor() {

        etMajor1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count_major = 1;
                Intent intent = new Intent(RegisterActivity.this, MajorListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        etMajor2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count_major = 2;
                Intent intent = new Intent(RegisterActivity.this, MajorListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        etMajor3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count_major = 3;
                Intent intent = new Intent(RegisterActivity.this, MajorListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

    // [+]버튼 눌러 전공 추가
    public void addMajor() {
        bMajorAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 전공 입력 칸 추가 생성☆☆☆☆☆☆☆☆
                addMajor++;
                if (addMajor >= 2) {
                    etMajor2.setVisibility(View.VISIBLE);
                }
                if (addMajor >= 3) {
                    etMajor3.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // 회원 가입 정보 입력 확인
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

                // 전공 입력 확인
                if (etMajor1.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "전공을 입력하세요", Toast.LENGTH_SHORT).show();
                    etMajor1.requestFocus();
                    return;
                }

                Bundle extras = getIntent().getExtras();
                String email = extras.getString("email");
                String password = etPassword.getText().toString();
                String nickname = etNickname.getText().toString();
                String major1 = etMajor1.getText().toString();
                String major2 = etMajor2.getText().toString();
                String major3 = etMajor3.getText().toString();

                //닉네임 중복 확인 유무
                if (nickname_overlap == false) {
                    Toast.makeText(RegisterActivity.this, "닉네임 중복 확인이 필요합니다.", Toast.LENGTH_SHORT).show();
                    etNickname.requestFocus();
                    return;
                } else {
                    // 서버로 회원 정보 전달
                    try {
                        url_register = "http://52.78.207.133:3000/members/addInfo?";
                        url_register += "email=" + URLEncoder.encode(email, "utf-8");
                        url_register += "&password=" + URLEncoder.encode(password, "utf-8");
                        url_register += "&nickname=" + URLEncoder.encode(nickname, "utf-8");
                        url_register += "&major1=" + URLEncoder.encode(major1, "utf-8");
                        url_register += "&major2=" + URLEncoder.encode(major2, "utf-8");
                        url_register += "&major3=" + URLEncoder.encode(major3, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    registerTask = new RegisterTask();
                    registerTask.execute();

                    // 자동 로그인위해 SharedPreferences 사용하여 UserInfo 데이터 저장
                    SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                    SharedPreferences.Editor editor = test.edit();
                    editor.putString("UserEmail", email); // UserEmail 파일에 email 데이터를 저장
                    editor.putString("UserNickname", nickname); // UserNickname 파일에 nickname 데이터를 저장
                    editor.putString("UserMajor1", major1); // UserMajor1 파일에 major1 데이터를 저장
                    editor.putString("UserMajor2", major2); // UserMajor2 파일에 major2 데이터를 저장
                    editor.putString("UserMajor3", major3); // UserMajor3 파일에 major3 데이터를 저장

                    editor.commit(); // UserInfo 데이터 저장 완료

                }
            }
        });
    }

    // 최대 3개의 전공 선택
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {// 액티비티가 정상적으로 종료되었을 경우
            if (requestCode == 1000) {// MajorListActivity에서 호출한 경우에만 처리
                // 받아온 전공명을 MajorListActivity에 표시
                if (count_major == 1) {
                    etMajor1.setText(data.getStringExtra("selected_major"));
                } else if (count_major == 2) {
                    etMajor2.setText(data.getStringExtra("selected_major"));
                } else if (count_major == 3) {
                    etMajor3.setText(data.getStringExtra("selected_major"));
                }
            }
        }
    }
}

