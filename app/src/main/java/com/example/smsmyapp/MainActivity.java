package com.example.smsmyapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;

public class MainActivity extends Activity {
    EditText editTextMsg;
    EditText editTextName;
    RecyclerView recyclerView;
    TopRecyclerAdapter adapter;
    ArrayList<SmsClass> smsClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextName);
        editTextMsg = findViewById(R.id.editTextMessage);
        recyclerView = findViewById(R.id.recyclerView);
        smsClasses = new ArrayList<>();
        initRecyclerView();
    }

    public void onClickSend(View view) {
            int position = adapter.getItemCount();
            if (editTextName.getText().toString().length() > 2
                    && editTextMsg.getText().toString().length() > 3) {
                String nameStr = getString(R.string.you_to) + editTextName.getText().toString();
                String msgStr = editTextMsg.getText().toString();
                smsClasses.add(new SmsClass(nameStr, msgStr));
                adapter.notifyItemInserted(position);
                recyclerView.scrollToPosition(position);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(nameStr, null, msgStr, null, null);
                onClickClear(view);
            } else {
                Toast.makeText(getBaseContext(),
                        getString(R.string.check_file), Toast.LENGTH_SHORT).show();
            }
    }

    public void onClickClear(View view) {
        editTextName.setText("");
        editTextMsg.setText("");
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        adapter = new TopRecyclerAdapter(smsClasses);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new TopRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (smsClasses.get(position).getName().startsWith(getString(R.string.you_to))) {
                    String[] strArr =  smsClasses.get(position).getName().split(":");
                    editTextName.setText(strArr[strArr.length-1]);
                } else {
                    editTextName.setText(smsClasses.get(position).getName());
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        int position = adapter.getItemCount();
        smsClasses.add(new SmsClass(event.name, event.message));
        adapter.notifyItemInserted(position);
        recyclerView.scrollToPosition(position);
    }

    public void onClickClearAll(View view) {
        smsClasses.clear();
        adapter.notifyDataSetChanged();
    }
}
