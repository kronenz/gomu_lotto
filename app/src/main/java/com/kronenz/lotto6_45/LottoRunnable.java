package com.kronenz.lotto6_45;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by byeonsanghyeon on 15. 3. 27..
 */
public class LottoRunnable implements Runnable {


    private List<String> mNumberList;
    private Random rnd = new Random();

    private static final int NUMBER_COUNT = 45;
    private static final int CALL_COUNT = 6;

    private static final int CYCLE_COUNT = 30;

    private static final int CYCLE_INTERVAL = 100; //milli second

    private Handler mHandler;

    public static final int RUN_JUST = 1000;
    public static final int RUN_CALL = 1001;
    public static final int FINISH = 1002;

    public LottoRunnable(Handler handler){
        mHandler = handler;

        mNumberList = new ArrayList<>();

        for(int i = 0; i < NUMBER_COUNT; i++){
            String numberStr = String.valueOf(i+1);
            mNumberList.add(numberStr);
        }
    }

    private int ballCount = 44;
    private String randomOneString()
    {
        String result = "";

        int number =  rnd.nextInt(ballCount);
        result = mNumberList.get(number);
        ballCount--;

        mNumberList.remove(number);

        return result;
    }

    private String justRandomString(){
        int number = rnd.nextInt(ballCount);
        return mNumberList.get(number);
    }

    @Override
    public void run() {



        for(int i = 0; i < CALL_COUNT; i++){

            for(int j = 0; j < CYCLE_COUNT; j++){

                String number = justRandomString();

                Message msg = new Message();
                msg.what = RUN_JUST;
                msg.obj = number;

                mHandler.sendMessage(msg);
                try {
                    Thread.sleep(CYCLE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            String callNumber = randomOneString();

            Message msg = new Message();
            msg.what = RUN_CALL;
            msg.obj = callNumber;

            mHandler.sendMessage(msg);
        }//end of for

        try {
            Thread.sleep(CYCLE_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mHandler.sendEmptyMessage(FINISH);

    }
}
