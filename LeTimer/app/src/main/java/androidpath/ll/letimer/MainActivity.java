package androidpath.ll.letimer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.time_setter)
    EditText time_setter;
    @InjectView(R.id.btn_setTime)
    Button btn_setTime;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.btn_start)
    Button btn_start;
    @InjectView(R.id.btn_stop)
    Button btn_stop;
    private Timer timer = null;
    private TimerTask timerTask = null;
    private int countDown = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_setTime)
    void getInputTime(View v) {
        countDown = Integer.parseInt(time_setter.getText().toString());
        time.setText(time_setter.getText().toString());
    }

    @OnClick(R.id.btn_start)
    void countStart(View v) {
        disableButton();
        startTimer();
    }

    @OnClick(R.id.btn_stop)
    void countStop(View v) {
        time.setText("_");
        stopTimer();

    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            time.setText(msg.arg1 + "");
            startTimer();
        }
    };

    public void startTimer() {

        if (countDown < 0) {
            Toast.makeText(this, "Time has to be positive.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (countDown == 0) {
            stopTimer();
            Toast.makeText(this, "Time up", Toast.LENGTH_SHORT).show();
            return;
        }

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                countDown--;
                Message message = mHandler.obtainMessage();
                message.arg1 = countDown;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 1000);
    }

    public void stopTimer() {
        timer.cancel();
        enableButton();
    }

    private void disableButton() {
        btn_setTime.setEnabled(false);
        btn_start.setEnabled(false);
    }

    private void enableButton() {
        btn_setTime.setEnabled(true);
        btn_start.setEnabled(true);
    }
}
