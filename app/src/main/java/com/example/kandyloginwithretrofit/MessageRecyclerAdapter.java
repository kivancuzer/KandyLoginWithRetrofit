package com.example.kandyloginwithretrofit;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rbbn.cpaas.mobile.messaging.api.Conversation;

import java.util.Date;
import java.util.List;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MessageHolder> {

    List<Conversation> conversationList;

    public MessageRecyclerAdapter(List<Conversation> conversationList) {
        this.conversationList = conversationList;
    }

    @NonNull
    @Override
    public MessageRecyclerAdapter.MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.message_item, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageRecyclerAdapter.MessageHolder holder, int position) {
        String name[] = conversationList.get(position).getParticipant().split("@");
        holder.txtMessageName.setText(name[0]);
        holder.txtLastMessage.setText(conversationList.get(position).getLastText());
        long time = conversationList.get(position).getLastMessageTime();
        holder.txtLastMessageTime.setText(DateFormat.format("MM/dd/yyyy", new Date(time)).toString());
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        TextView txtMessageName;
        TextView txtLastMessage;
        TextView txtLastMessageTime;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            txtLastMessage = itemView.findViewById(R.id.txtLastMessage);
            txtMessageName = itemView.findViewById(R.id.txtMessageName);
            txtLastMessageTime = itemView.findViewById(R.id.txtLastMessageTime);
        }
    }
}
