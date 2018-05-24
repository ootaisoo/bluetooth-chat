package ru.worksolutions.bluetothchatdemo;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static ru.worksolutions.bluetothchatdemo.BluetothChatService.MESSAGE_READ;
import static ru.worksolutions.bluetothchatdemo.BluetothChatService.MESSAGE_WRITE;

public class ChatActivity extends AppCompatActivity {

    private MessageAdapter messageAdapter;
    private EditText editText;
    private Button sendButton;
    private List<String> messages;
    private RecyclerView recyclerView;
    private BluetothChatService bluetothChatService;
    BluetoothDevice device;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        Intent intent = getIntent();
        device = intent.getParcelableExtra("device");

        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(messages);
        editText = findViewById(R.id.edit_text);
        sendButton = findViewById(R.id.send);
        recyclerView = findViewById(R.id.output);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                sendMessage(message);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetothChatService = new BluetothChatService(handler);
        bluetothChatService.start();
        bluetothChatService.connect(device);
    }

    private void sendMessage(String message) {
        if (message.length() > 0) {
            bluetothChatService.writeMessage(message);
            editText.setText("");
            messageAdapter.add("Me:  " + message);
        }
    }

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case MESSAGE_WRITE:
//                    byte[] writeBuf = (byte[]) msg.obj;
//                    // construct a string from the buffer
//                    String writeMessage = new String(writeBuf);
//
//                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    if (readMessage.length() > 0) {
                        messageAdapter.add(device + ":  " + readMessage);
                    }
                    break;
            }
        }
    };
}
