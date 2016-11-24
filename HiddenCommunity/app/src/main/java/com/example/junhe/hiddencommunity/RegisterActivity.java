package com.example.junhe.hiddencommunity;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

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

    String url, result;


    private ProgressDialog mProgressDialog;

    class RegisterTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = HTTPInstance.Instance().Post(url);
                onResponseHttp(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    RegisterTask registerTask;

    private void onResponseHttp(String s) {
        if (s == null) {
//            mProgressDialog = ProgressDialog.show(.this,"",
//                    "잠시만 기다려 주세요.",true);
            System.out.println("회원 정보를 정확히 입력해주세요");
            return;
        }
        Log.d("RESPONSE", s);
        if (s.compareTo("ok") == 0) {
            System.out.println("가입이 완료되었습니다.");
            Intent intent = new Intent(getApplicationContext(), NoticeBoardActivity.class);
            startActivityForResult(intent, 1000);
        } else {

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

                Bundle extras = getIntent().getExtras();
                String email = extras.getString("email");
                String password = etPassword.getText().toString();
                String nickname = etNickname.getText().toString();
                String major1 = etMajor1.getText().toString();
                String major2 = etMajor2.getText().toString();
                String major3 = etMajor3.getText().toString();

                // 자동 로그인위해 SharedPreferences 사용하여 "UserInfo"에 email 저장
                SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor = test.edit();
                editor.putString("UserInfo", email); //UserInfo라는 파일에 email 데이터를 저장한다.
                editor.commit(); //완료한다.

                // 서버로 회원 정보 전달========> 이게 묶어서 user 당 저장되어 있으면 파싱으로 받아올 수 있지 않나
                try {
                    url = "http://52.78.207.133:3000/members/addInfo?";
                    url += "email=" + URLEncoder.encode(email, "utf-8");
                    url += "&password=" + URLEncoder.encode(password, "utf-8");
                    url += "&nickname=" + URLEncoder.encode(nickname, "utf-8");
                    url += "&major1=" + URLEncoder.encode(major1, "utf-8");
                    url += "&major2=" + URLEncoder.encode(major2, "utf-8");
                    url += "&major3=" + URLEncoder.encode(major3, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                registerTask = new RegisterTask();
                registerTask.execute();

                // JSON 변환 후 서버에 보내기
                String userInfo_temp = "{\"email\"" + ":" + "\"" + email + "\"" + ","
                        + "\"password\"" + ":" + "\"" + password + "\"" + ","
                        + "\"nickname\"" + ":" + "\"" + nickname + "\"" + ","
                        + "\"major1\"" + ":" + "\"" + major1 + "\"" + ","
                        + "\"major2\"" + ":" + "\"" + major2 + "\"" + ","
                        + "\"major3\"" + ":" + "\"" + major3 + "\"" + "}";
                System.out.println(userInfo_temp);

                // 서버에서 받아온 JSON 파싱
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    String resultUserEmail = jsonObject.getString("email");
                    String resultUserNickname = jsonObject.getString("nickname");
                    String resultUserPassword = jsonObject.getString("password");
                    String resultUserMajor1 = jsonObject.getString("major1");
                    String resultUserMajor2 = jsonObject.getString("major2");
                    String resultUserMajor3= jsonObject.getString("major3");
                    System.out.println("서버에서 받아온 JSON 파싱!!");
                    System.out.println(resultUserEmail + "\n" + resultUserNickname + "\n" + resultUserPassword + "\n" + resultUserMajor1+ "\n" + resultUserMajor2+ "\n" + resultUserMajor3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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

