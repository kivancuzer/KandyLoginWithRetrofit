package com.example.kandyloginwithretrofit.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kandyloginwithretrofit.CPaaSManager;
import com.example.kandyloginwithretrofit.MessageRecyclerAdapter;
import com.example.kandyloginwithretrofit.R;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.messaging.api.Conversation;
import com.rbbn.cpaas.mobile.messaging.api.FetchConversationsCallback;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatService;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends Fragment {

    CPaaS cpaas;
    ChatService chatService;
    RecyclerView recyclerViewMessages;
    List<Conversation> conversationList;

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cpaas = CPaaSManager.getCpaas();
        chatService = cpaas.getChatService();
        getConversations();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewMessages = view.findViewById(R.id.recyclerViewMessages);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(view.getContext()));
        super.onViewCreated(view, savedInstanceState);
    }

    public void getConversations() {
        chatService.fetchConversations(new FetchConversationsCallback() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                conversationList = new ArrayList<>(conversations);
                System.out.println("Conversation List : " + conversationList.toString());
                System.out.println("Conversation List Last Text: " + conversations.get(0).getLastText());
                recyclerViewMessages.setAdapter(new MessageRecyclerAdapter(conversationList));
                //handle success
            }

            @Override
            public void onFail(MobileError error) {
                System.out.println("Conversation fail!");
            }
        });
    }

}