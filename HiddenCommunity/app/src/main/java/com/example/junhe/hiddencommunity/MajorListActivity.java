package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.MajorData;

import static android.R.attr.data;


public class MajorListActivity extends AppCompatActivity {

    private ArrayList<MajorData> major_data = new ArrayList<MajorData>();
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_list);

        // 전공 추가
        major_data.add(new MajorData("건축·토목", "건축·설비공학, 건축학, 조경학, 토목공학, 도시공학"));
        major_data.add(new MajorData("경영·경제", "경영학, 경제학, 관광학, 광고·홍보학, 금융·회계·세무학, 무역·유통학, 교양경상학"));
        major_data.add(new MajorData("교육", "교육학, 유아교육학, 특수교육, 초등교육학, 언어교육, 인문교육, 사회교육, 공학교육, 자연계교육, 예체능교육"));
        major_data.add(new MajorData("교통·운송", "지상교통공학, 항공학, 해양공학"));
        major_data.add(new MajorData("기계·금속", "기계공학, 금속공학, 자동차공학"));
        major_data.add(new MajorData("기타 공학", "산업공학, 기전공학, 응용공학, 교양공학"));
        major_data.add(new MajorData("기타 언어·문학", "언어학, 교양어·문학"));
        major_data.add(new MajorData("농림·수산", "농업학, 수산학, 산림·원예학"));
        major_data.add(new MajorData("동양 언어·문학", "국어·국문학, 일본어·문학, 중국어·문학, 기타아시아어·문학"));
        major_data.add(new MajorData("국어·국문학, 일본어·문학, 중국어·문학, 기타아시아어·문학", "ㅇ"));
        major_data.add(new MajorData("디자인", "디자인일반, 산업디자인, 시각디자인, 패션디자인, 기타디자인"));
        major_data.add(new MajorData("무용·체육", "무용, 체육"));
        major_data.add(new MajorData("미술·조형", "순수미술, 응용미술, 조형"));
        major_data.add(new MajorData("법률", "법학"));
        major_data.add(new MajorData("사회과학", "가족·사회·복지학, 국제학, 도시·지역학, 사회학, 언론·방송·매체학, 정치외교학, 행정학, 교양사회과학"));
        major_data.add(new MajorData("생물·화학·환경", "생명과학, 생물학, 동물·수의학, 자원학, 화학, 환경학"));
        major_data.add(new MajorData("생활과학", "가정관리학, 식품영양학, 의류·의상학, 교양생활과학"));
        major_data.add(new MajorData("서양 언어·문학", "영미어·문학, 독일어·문학, 러시아어·문학, 스페인어·문학, 프랑스어·문학, 기타유럽어·문학"));
        major_data.add(new MajorData("수학·물리·천문·지리", "수학, 통계학, 물리·과학, 천문·기상학, 지구·지리학, 교양자연과학"));
        major_data.add(new MajorData("연극·영화", "연극·영화"));
        major_data.add(new MajorData("음악", "음악학, 국악, 기악, 성악, 작곡, 기타음악"));
        major_data.add(new MajorData("응용예술", "공예, 사진·만화, 영상·예술"));
        major_data.add(new MajorData("의학", "의학, 치의학, 한의학, 간호학, 약학, 보건학, 재활학, 의료공학"));
        major_data.add(new MajorData("인문과학", "문헌정보학, 문화·민속·미술사학, 심리학, 역사·고고학, 종교학, 국제지역학, 철학·윤리학, 교양인문학"));
        major_data.add(new MajorData("전기·전자", "전기공학, 전자공학, 제어계측공학"));
        major_data.add(new MajorData("정밀·에너지·소재", "광학공학, 에너지공학, 반도체·세라믹공학, 섬유공학, 신소재공학, 재료공학"));
        major_data.add(new MajorData("컴퓨터·통신", "전산학·컴퓨터공학, 응용소프트웨어공학, 정보·통신공학"));
        major_data.add(new MajorData("화학", "화학공학"));

        // ListView 가져오기
        final ListView major_list = (ListView) findViewById(R.id.Majorlist);

        MajorListAdapter adapter = new MajorListAdapter(mContext, 0, major_data);
        // ListView에 각각의 전공표시를 제어하는 Adapter를 설정
        major_list.setAdapter(adapter);

        // 전공 클릭시 이벤트 리스너 등록
        major_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView major_list = (ListView) parent;
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                // 전공 선택시 RegisterActivity의 etMajor에 전달되어야 함☆☆☆☆☆
                String major = (String) major_data.get(position).getMajor();
                intent.putExtra("major",major);
                startActivityForResult(intent, 1000);


            }
        });

    }

    private class MajorListAdapter extends ArrayAdapter<MajorData> {

        private ArrayList<MajorData> mMajorData;

        public MajorListAdapter(Context context, int resource, ArrayList<MajorData> majorData) {
            super(context, resource, majorData);

            mMajorData = majorData;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView= inflater.inflate(R.layout.list_major, null, true);

            TextView txtMajor = (TextView) rowView.findViewById(R.id.Major);
            TextView txtSubMajor = (TextView) rowView.findViewById(R.id.SubMajor);

            txtMajor.setText( mMajorData.get(position).getMajor() );
            txtSubMajor.setText(mMajorData.get(position).getSubMajor());

            return rowView;
//            return super.getView(position, convertView, parent);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_coupon_lists, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    }


