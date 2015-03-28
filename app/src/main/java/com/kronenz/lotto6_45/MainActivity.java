package com.kronenz.lotto6_45;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.kronenz.lotto6_45.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private Button mStartBtn;

    private TextView mTxtView;

    private RecyclerView mRecyclerView;
    private LottoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartBtn = (Button) findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new LottoAdapter();

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linear = new LinearLayoutManager(getApplicationContext());
        linear.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linear);
        mRecyclerView.setAdapter(mAdapter);

        mTxtView = (TextView) findViewById(R.id.txt_switcher);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_btn:
                doStart();
                break;
        }
    }

    private void doStart(){

        mAdapter.clearList();
        mStartBtn.setVisibility(View.GONE);
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what){
                    case LottoRunnable.RUN_JUST:
                        mTxtView.setText((String)msg.obj);
                        break;
                    case LottoRunnable.RUN_CALL:
                        mTxtView.setText((String)msg.obj);


                        mAdapter.addItem((String)msg.obj);
                        break;
                    case LottoRunnable.FINISH:
                        mStartBtn.setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }
        });

        Runnable run = new LottoRunnable(handler);

        Thread thread = new Thread(run);
        thread.start();
    }


    private class LottoAdapter extends  RecyclerView.Adapter<LottoViewHolder>{

        private List<String> mList;

        public LottoAdapter(){
            mList = new ArrayList<>();
        }

        @Override
        public LottoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.lotto_item, viewGroup, false);
            return new LottoViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LottoViewHolder lottoViewHolder, int i) {
            String number = mList.get(i);
            int numberColor = Integer.parseInt(number);
            int color = 0;
            switch (numberColor){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    //Yellow
                    color = getResources().getColor(R.color.material_yellow_500);
                    break;
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    //blue
                    color = getResources().getColor(R.color.material_blue_500);
                    break;
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    //red
                    color = getResources().getColor(R.color.material_red_500);
                    break;
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                    //black
                    color = getResources().getColor(R.color.material_black_500);
                    break;
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                    //green
                    color = getResources().getColor(R.color.material_green_500);
                    break;
            }

            lottoViewHolder.mTxtView.setBackgroundColor(color);
            lottoViewHolder.mTxtView.setText(mList.get(i));
        }

        public void addItem(int index, String number){
            mList.add(number);
            notifyItemInserted(index);
        }
        public void addItem(String number){
            mList.add(number);
            notifyItemInserted(mList.size() - 1);

        }

        public void clearList(){
            mList.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class LottoViewHolder extends RecyclerView.ViewHolder{

        protected TextView mTxtView;
        protected CardView mCardView;

        public LottoViewHolder(View itemView) {
            super(itemView);

            mTxtView = (TextView) itemView.findViewById(R.id.txt_lotto);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
}
