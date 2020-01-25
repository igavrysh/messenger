package com.igavrysh.messenger.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.igavrysh.messenger.R
import com.igavrysh.messenger.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ChatLog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user.username

        setupDummyData()

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message")
            performSendMessage()
        }
    }

    class ChatMessage(val text: String)

    private fun performSendMessage() {
        val text = edittext_chat_log.text.toString()

        val reference = FirebaseDatabase.getInstance()
            .getReference("/messages")
            .push()

        val chatMessage = ChatMessage(text)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to send message: ${it.message}")
            }
    }

    private fun setupDummyData() {
        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatToItem("TO MESSAGE"))
        adapter.add(ChatFromItem("FROM MESSAGE...."))
        adapter.add(ChatToItem("abc"))
        adapter.add(ChatFromItem("abc"))
        adapter.add(ChatFromItem("abc"))
        adapter.add(ChatToItem("abc"))
        adapter.add(ChatToItem("abc"))
        adapter.add(ChatFromItem("abc"))
        adapter.add(ChatToItem("abc"))
        adapter.add(ChatToItem("abc"))
        adapter.add(ChatFromItem("abc"))
        adapter.add(ChatFromItem("abc"))
        adapter.add(ChatFooterItem())

        recyclerview_chat_log.adapter = adapter
    }
}

class ChatFromItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}


class ChatToItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}

class ChatFooterItem: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
    }

    override fun getLayout(): Int {
        return R.layout.chat_footer_row
    }
}