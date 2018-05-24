package ru.worksolutions.bluetothchatdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<String> messages;

    public MessageAdapter(List<String> meesages) {
        this.messages = meesages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_holder, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String message = messages.get(position);
        holder.message.setText(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void add(String message){
        messages.add(message);
        notifyItemInserted(messages.indexOf(message));
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView message;

        public MessageViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }
    }
}
