package com.busyo.yeonho.calview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends ActionBarActivity {

    GridAdapter gdadapter;    GridView gridView;

    ListAdapter lsadapter;    ListView listView;

    //그리드뷰에 보여질 날짜(IteamDate) 배열로 지정
    Integer [] ID;
    //날짜가 전월의 날짜인지 이번달인지 다음달의 날짜인지 상태값 Status
    Integer [] ST;
    ///요일코드숫자 표시
    Integer [] YI;

    //전월버튼
    Button btpre;
    //년월표시
    TextView tvym;
    //익월버튼
    Button btnext;
    //지금 년도
    int[] curyear = {Calendar.getInstance().get(Calendar.YEAR)};
    //지금 월
    int[] curmonth = {Calendar.getInstance().get(Calendar.MONTH) + 1};
    //지금 날짜
    int[] curdate = {Calendar.getInstance().get(Calendar.DATE)};
    //마지막 날짜 텍스트뷰
    TextView maxdate;

    //요일코드에 따른 요일이름. 1이면 일요일,2이면 화요일 이것.
    String dayname;

    //데이터 저장할 배열만들기
    ArrayList <String> arraydate;
    ArrayList <String> arraytime;
    ArrayList <String> arraysc;

    //리스트 뷰에 담을 객체들
    String llistsc;
    String clistsc;
    String rlistsc;

    int gdoitcyear=curyear[0];
    int gdoitcmonth=curmonth[0];
    int gdoitcdate=curdate[0];
    int gdoitcst;

    //인텐트로 넘겨받은 값
    String inputsc;
    String inputhh;
    String inputmm;
    String inputrb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arraydate = new ArrayList<String>();
        arraytime=new ArrayList<String>();
        arraysc=new ArrayList<String>();

        setTitle("Day06 정연호"); //타이틀

        btpre=(Button)findViewById(R.id.btpre);
        btnext=(Button)findViewById(R.id.btnext);
        tvym=(TextView)findViewById(R.id.tvym);
        maxdate = (TextView)findViewById(R.id.maxdate);
        maxdate.setText("오늘 날짜 : " + curyear[0] + "년 " + curmonth[0] + "월 " + curdate[0] + "일");

        //초기에는 현재의 년월 표시
        nowym();

        //년월표시 텍스트뷰를 눌렀을때 초기값으로 현재 년월 표시
        tvym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowym();
                gdadapter.notifyDataSetChanged();lsadapter.notifyDataSetChanged();

            }
        });

        btpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prem();
                gdadapter.notifyDataSetChanged();
            }
        });

        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextm();
                gdadapter.notifyDataSetChanged();
            }
        });

        //그리드 아답터 변수를 생성한후 activity_main.xml의 그리드 뷰에 적용시킨다.
        gridView = (GridView)findViewById(R.id.gridview);
        gdadapter= new GridAdapter(this);
        gridView.setAdapter(gdadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int s = 0;
                int y = 0;
                gdadapter.date(position, s, y);
                gdoitcdate = ID[position];
                gdoitcst = ST[s];
                gdoitcyear = curyear[0];
                gdoitcmonth = curmonth[0];
                String dt;
                if (ID[position].toString().length() == 1) {
                    dt = "0" + ID[position].toString();
                } else {
                    dt = ID[position].toString();
                }

                String mt;
                if (String.valueOf(curmonth[0]).length() == 1) {
                    mt = "0" + String.valueOf(curmonth[0]);
                } else {
                    mt = String.valueOf(curmonth[0]);
                }
                maxdate.setText("선택 날짜 : " + gdoitcyear + "년 " + mt + "월 " + dt + "일");
                lsadapter.notifyDataSetChanged();
            }
        });

        //리스트 아답터 변수를 생성한후 activity_main.xml의 리스트 뷰에 적용시킨다.
        listView = (ListView)findViewById(R.id.listview);
        lsadapter= new ListAdapter(this);
        listView.setAdapter(lsadapter);
    }
    //이번달 달력 표시
    public void nowym(){
        curyear[0] = Calendar.getInstance().get(Calendar.YEAR);
        curmonth[0] = Calendar.getInstance().get(Calendar.MONTH) + 1;

        if (String.valueOf(curmonth[0]).length() == 1){
            tvym.setText(curyear[0] +"년 "+ "0"+curmonth[0] +"월");
        }
        else if (String.valueOf(curmonth[0]).length() == 2){
            tvym.setText(curyear[0] +"년 "+ curmonth[0] +"월");
        }

    }

    //전월달력표시
    public void prem(){
        if (curmonth[0] - 1 == 0) {
            curyear[0] = curyear[0] - 1;
            curmonth[0] = 12;
        } else if (curmonth[0] - 1 != 0) {
            curmonth[0]--;
        }
        if (String.valueOf(curmonth[0]).length() == 1){
            tvym.setText(curyear[0] +"년 "+ "0"+curmonth[0] +"월");
        }
        else if (String.valueOf(curmonth[0]).length() == 2){
            tvym.setText(curyear[0] +"년 "+ curmonth[0] +"월");
        }
    }

    //다음달달력월표시
    public void nextm(){
        if (curmonth[0] +1 == 13){
            curyear[0] = curyear[0] +1;
            curmonth[0] = 1;
        }
        else if (curmonth[0] +1 != 13){
            curmonth[0]++;
        }
        if (String.valueOf(curmonth[0]).length() == 1){
            tvym.setText(curyear[0] +"년 "+ "0"+curmonth[0] +"월");
        }
        else if (String.valueOf(curmonth[0]).length() == 2){
            tvym.setText(curyear[0] +"년 "+ curmonth[0] +"월");
        }
    }

    //입력한 년과 월의 마지막 날짜 구하기
    public int getMonthLastDay(int year, int month) {
        month--;//입력받은 월에서 1을 뺀다. 날짜함수의 월은 0 부터 11까지이다. 1월을 찾으려면 1월을 입력받으면 0에 해당하는것을 찾아야한다
        //1월부터 12월까지
        int[] d = {31,28,31,30,31,30,31,31,30,31,30,31};
        //윤년계산
        if (((0 == (year%4)) && ( (0 != (year%100)) || (0 == (year%400)))) && month == 1) {
            return 29; //윤년이면 2월(month-1)엔 29반환
        } else {
            return d[month]; //아니면 해당월의 날짜 반환
        }
    }

    //입력한 년과 월의 1일의 요일에 해당하는 숫자 구하기. 1일을 무슨요일 부터 시작할지를 구하기위해
    public int getFirstName(int year, int month){
        month--;
        Calendar d =Calendar.getInstance();
        d.set(year,month,1);//년월일 지정
        int a = d.get(Calendar.DAY_OF_WEEK);//지정한 년월일의 해당요일의 숫자코드 반환 일요일은 1,토요일은 7
        return a;
    }

    //입력한 년,월로 그 달의 1일의 요일코드를 구해낸것(getFirstName(int year, int month))을 바탕으로 요일 나타내기
    public String getDayname(int year,int month,int date){
        int b = getFirstName(year, month);
        month--;
        switch (b){
            case 1 : dayname="일요일";break;
            case 2 : dayname="월요일";break;
            case 3 : dayname="화요일";break;
            case 4 : dayname="수요일";break;
            case 5 : dayname="목요일";break;
            case 6 : dayname="금요일";break;
            case 7 : dayname="토요일";break;
        }
        return dayname;
    }
    //입력한 년,월,일로 요일코드를 구해낸것(getFirstName(int year, int month))을 바탕으로 요일 나타내기
    public Integer getGetDay(int year,int month,int date){
        month--;
        Calendar d =Calendar.getInstance();
        d.set(year,month,date);//년월일 지정
        int b = d.get(Calendar.DAY_OF_WEEK);//지정한 년월일의 해당요일의 숫자코드 반환 일요일은 1,토요일은 7
        return b;
    }


    //그리드 아답터클래스를 구성한다. 그리고나서 그리드아답터에 그리드뷰를 꽂는다
    public class GridAdapter extends BaseAdapter {    //BaseAdapter의 상속을 받은 GridAdapter를 정의 한다
        //컨텍스트 변수를 선언, this 컨텍스트를  생성자에서 받은후에 mcontext변수에 대입.
        //이는 mcontext변수를 다른 메소드에서 사용하기 위함
        Context mcontext;
        public GridAdapter(Context c){//아답터 객체생성
            mcontext =c;
        }

        //여기부터 그리드뷰 안에 한칸 한칸 보여줄 값을 구해온다
        public void date(int a, int s, int y){//getView 에서 position 값과 s 값을 넘겨받음
            ID = new Integer[42];   ST = new Integer[42];   YI = new Integer[42];
            //position값이 (요일값-1) 보다 작으면 앞의 빈칸에 전월 날짜를 넣는다 요일값은 1부터 7까지이고 position값은 0부터 시작해서 1을 빼주는거다
            if (a< ((getFirstName(curyear[0], curmonth[0]))-1))
            {
                int preyear = 0, premonth = 0;
                if (curmonth[0] - 1 == 0) {
                    preyear= curyear[0] - 1;
                    premonth= 12;
                } else if (curmonth[0] - 1 != 0) {
                    preyear = curyear[0] ;
                    premonth = curmonth[0]-1;
                }

                //남는칸에 전월의 날짜 구하기
                if (a==0) {
                    ID[a] = (getMonthLastDay(preyear, premonth)) - ((getFirstName(curyear[0], curmonth[0])) - 1) + 1;
                    ST[s]=-1;
                    YI[y] = getGetDay(preyear,premonth,ID[a]);
                }
                else{
                    ID[a] = (getMonthLastDay(preyear, premonth)) - ((getFirstName(curyear[0], curmonth[0])) - 1) + 1+a;
                    ST[s]=-1;
                    YI[y] = getGetDay(preyear,premonth,ID[a]);
                }
            }
            else if(a>= ((getFirstName(curyear[0], curmonth[0]))-1))
            {
                //이번달 날짜 구하기
                ID[a] = a - ((getFirstName(curyear[0], curmonth[0])) - 1)+1;
                ST[s]=0;
                YI[y] = getGetDay(curyear[0],curmonth[0],ID[a]);

                if(ID[a]>(getMonthLastDay(curyear[0], curmonth[0])))
                {
                    //남는칸에 다음달 날짜 구하기
                    ID[a]=a-(getMonthLastDay(curyear[0], curmonth[0]))    - ((getFirstName(curyear[0], curmonth[0])) - 1)   +1;
                    ST[s]=1;
                    int nextyear=0;
                    int nextmonth=0;
                    if (curmonth[0] +1 == 13)
                    {
                        nextyear = curyear[0] +1;
                        nextmonth = 1;
                    }
                    else if (curmonth[0] +1 != 13)
                    {
                        nextyear = curyear[0];
                        nextmonth = curmonth[0]+1;
                    }
                    YI[y] = getGetDay(nextyear,nextmonth,ID[a]);
                }
            }
            return ;
        }
        @Override
        public int getCount() {
            return 42;//개수를 반환(총 몇개냐). 1일이 토요일에시작하면 6줄이 필요한 경우가 있으니까 7*6 해서 42번 반환하고 1일의
            //시작요일에따라 배열의 시작위치를 정하고 앞의 빈칸 에는 전월의 날짜 뒤의 빈칸에는 다음달의 날짜를 넣는다
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }

        //한칸 한칸 내용을 뷰에 넣는다
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            int s=0, y=0;
            date(position, s,position);//현재 위치번호와 , 상태값(날짜가 이전월,현재월,다음월) 인자

            //그리드의 각 칸마다 보여준다
            TextView textView = new TextView(mcontext);
            textView.setLayoutParams(new GridView.LayoutParams(100, 100));
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.WHITE);
            if (YI[position] == 1){//일요일
                textView.setBackgroundColor(Color.argb(255, 255, 188, 188)); //붉은계통으로
            }
            else if (YI[position] == 7) {//토요일)
                textView.setBackgroundColor(Color.argb(255, 200 , 236, 255));//푸른계통으로
            }
            //숫자가 1자리이면 앞에 0을 붙이고 아니면 그냥 그대로 표시
            if (ID[position].toString().length() == 1){
                textView.setText("0" + ID[position].toString());
            }
            else{
                textView.setText(ID[position].toString());
            }
            //날짜가 전월인지,이번달인지,다음달인지의 상태값
            if (ST[s] ==-1){ //-1은 전월
                textView.setTextSize(20);
                textView.setTextColor(Color.GRAY);
            }
            else if (ST[s] ==0){//0은 이번달
                String dt;
                if (ID[position].toString().length() == 1) {
                    dt="0" + ID[position].toString();
                }
                else{
                    dt=ID[position].toString();
                }

                String mt;
                if (String.valueOf(curmonth[0]).length() == 1){
                    mt="0" + String.valueOf(curmonth[0]);
                }
                else{
                    mt=String.valueOf(curmonth[0]);
                }

                textView.setTextSize(30);
                textView.setTextColor(Color.BLACK);

                int j = 0;//초기값

                for (int i = 0; i < arraydate.size(); i++) {
                    Log.d("11111111",arraydate.get(i)+"__"+(curyear[0] + "" + mt + "" + dt));
                    if (arraydate.get(i).equals(curyear[0] + "" + mt + "" + dt)) {//선택한 날짜에 해당하는 자료가 array 에 있다면
                        j = 1;//j를 1로 바꾸어 주황색 및,굵은 글자를 나타낼수 있게 한다
                    }
                }
                if (j==1){
                    textView.setBackgroundColor(Color.argb(255, 255, 180, 60));
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }
            else if (ST[s] == 1){//1은 다음달
                textView.setTextSize(20);
                textView.setTextColor(Color.GRAY);
            }
            return textView;
        }
    }

    //리스트 아답터클래스를 구성한다. 그리고나서 리스트아답터에 리스트뷰를 꽂는다
    public class ListAdapter extends BaseAdapter {    //BaseAdapter의 상속을 받은 ListAdapter를 정의 한다
        //컨텍스트 변수를 선언, this 컨텍스트를  생성자에서 받은후에 mcontext변수에 대입.
        //이는 mcontext변수를 다른 메소드에서 사용하기 위함
        Context lcontext;

        public ListAdapter(Context c) {//아답터 객체생성
            lcontext = c;
        }

        public void listd(int a){//리스트 뷰에서 이 메쏘드를 불러 값을 구한다

            gdoitcyear  =curyear[0];
            gdoitcmonth =curmonth[0];

            String zd,zm;
            if (gdoitcst == 0){//이번달것만 리스트뷰를 보여주자

                    if (String.valueOf(gdoitcdate).length()==1){
                        zd="0"+gdoitcdate;
                    }
                    else{
                        zd=""+gdoitcdate;
                    }

                    if (String.valueOf(gdoitcmonth).length()==1){
                        zm="0"+gdoitcmonth;
                    }
                    else{
                        zm=""+gdoitcmonth;
                    }
                Log.d("00000000",((gdoitcyear+""+zm+""+zd).toString())+"__"+arraydate.get(a).toString());
                    if ((gdoitcyear+""+zm+""+zd).toString().equals(arraydate.get(a).toString())){//켈린더에 선택한 날짜에 해당하는 값이 array에 있으면

                        llistsc = arraydate.get(a);
                        clistsc = arraytime.get(a);
                        rlistsc = arraysc.get(a);
                        llistsc=llistsc.substring(0,4)+"/"+llistsc.substring(4,6)+"/"+llistsc.substring(6,8)+" ";
                        clistsc=clistsc.substring(0,2)+" "+clistsc.substring(2,4)+":"+ clistsc.substring(4,6)+" ";
                    }
                    else{
                        llistsc=null;
                    }
            }else{
                llistsc=null;
            }
            return ;
        }

        @Override
        public int getCount() {
            return arraydate.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        //한칸 한칸 내용을 리스트뷰에 넣는다
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            listd(position);
            if (llistsc == null){
                TextView ltextView = new TextView(lcontext);
                return ltextView;
            }
            else{
                TextView ltextView = new TextView(lcontext);
                ltextView.setGravity(Gravity.LEFT);
                ltextView.setTextSize(18);
                ltextView.setBackgroundColor(Color.WHITE);
                ltextView.setText(llistsc + clistsc + rlistsc);
                ltextView.setPadding(5, 5, 5, 5);
                return ltextView;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //메뉴 에서 아이템을 선택하여 수행
        //noinspection SimplifiableIfStatement
        if (id == R.id.scin) {

            // 액티비티를 띄워주도록 startActivityForResult() 메소드를 호출합니다.
            Intent dialintent = new Intent(getBaseContext(), dialog.class);
            String zd,zm;
            if (String.valueOf(gdoitcdate).length()==1){
                zd="0"+gdoitcdate;
            }
            else{
                zd=""+gdoitcdate;
            }

            if (String.valueOf(gdoitcmonth).length()==1){
                zm="0"+gdoitcmonth;
            }
            else{
                zm=""+gdoitcmonth;
            }
            dialintent.putExtra("year",gdoitcyear+"");
            dialintent.putExtra("month",zm + "");
            dialintent.putExtra("date",zd+"");
            startActivityForResult(dialintent, 1001);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==  1001 && resultCode==RESULT_OK)
        {
            inputsc =data.getStringExtra("inputsc");
            inputhh =data.getStringExtra("inputhh");
            if (inputhh.toString().length()==1){
                inputhh="0"+inputhh;
            }
            inputmm =data.getStringExtra("inputmm");
            if (inputmm.toString().length()==1){
                inputmm="0"+inputmm;
            }
            inputrb = data.getStringExtra("inputrb");


            String zd,zm;
            if (String.valueOf(gdoitcdate).length()==1){
                zd="0"+gdoitcdate;
            }
            else{
                zd=""+gdoitcdate;
            }

            if (String.valueOf(gdoitcmonth).length()==1){
                zm="0"+gdoitcmonth;
            }
            else{
                zm=""+gdoitcmonth;
            }
            int maxsz = arraydate.size();
            arraydate.add(maxsz,gdoitcyear+""+zm+""+zd);
            arraytime.add(maxsz,inputrb+""+inputhh+""+inputmm);
            arraysc.add(maxsz, inputsc+"");

            gdadapter.notifyDataSetChanged();
            lsadapter.notifyDataSetChanged();
        }
    }
}
