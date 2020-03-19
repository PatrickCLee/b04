package tw.org.iii.brad.brad04;
//執行序
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
    }

    public void test1(View view) {
       MyThread mt1 = new MyThread("A");
       MyThread mt2 = new MyThread("O");
       mt1.start();
       mt2.start();
       Log.v("brad","test1()");
    }

    private class MyThread extends Thread{
        String name;
        MyThread(String name){this.name = name;}
        @Override
        public void run() {
            for(int i=0; i<10; i++){
                Log.v("brad",name + "i = " + i);
                //tv.setText(name + "i = " + i);

                Message message = new Message();
                Bundle data = new Bundle();
                data.putCharSequence("data",name + "i = " + i);
                message.setData(data);
                uiHandler.sendMessage(message);
                //sendMessage參數要帶message物件,故上方要先有m物件，而message的setData要帶Bundle物件,故
                //由Bundle帶真正要傳的訊息

                //也可想像Message為一個物件,帶有what及arg1,arg2屬性,但此處不理會
                //在Message裡包一個Bundle,由Bundle去setData,然後Handler將Message送出去


                //uiHandler.sendEmptyMessage(0);//what代表what happened, 狀態訊息


                try {
                    Thread.sleep(800);
                }catch (Exception e){

                }

            }
        }
    }
    private UIHandler uiHandler = new UIHandler();//做出物件實體才能玩

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //Log.v("brad","OK");
            CharSequence data = msg.getData().getCharSequence("data","no data");//msg是傳遞的Message物件,getData拆解封包回傳Bundle,getCharSequence
            if (msg.what ==1) {
                tv2.setText(data);
            }else{
                tv.setText(data);
            }
        }
    }

    private Timer timer = new Timer();
    private class MyTask extends TimerTask {
        int i;
        @Override
        public void run() {
            Log.v("brad","i = " + i++);
            Message message = new Message();
            message.what = 1;           //what是Message的屬性,故可用為辨別為哪個Message
            Bundle data = new Bundle();
            data.putCharSequence("data", "i = " + i);
            message.setData(data);
            uiHandler.sendMessage(message);
        }
    }
    public void test2(View view) {
        timer.schedule(new MyTask(),1*1000,1*1000);
    }

    public void test3(View view) {

    }
}
