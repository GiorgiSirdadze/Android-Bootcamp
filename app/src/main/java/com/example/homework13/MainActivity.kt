package com.example.homework13

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework13.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

   private val messageAdapter by lazy{
       MessageAdapter()
   }

    private var messageIdCounter = 0

    private val messageList = mutableListOf<MessageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.sendButton.setOnClickListener {
            messageHandle()
        }

    }

    private fun messageHandle(){
        val id = messageIdCounter++
        val messageText = binding.messageText.text.toString().trim()
        val messageType = if (messageList.size % 2 == 0) MessageType.INCOMING else MessageType.OUTGOING
        val messageTime = getCurrentTime()

        val message = MessageItem(id, messageText, messageType, messageTime)

        messageList.add(message)

        binding.messageItems.layoutManager = LinearLayoutManager(this)
        binding.messageItems.adapter = messageAdapter
        messageAdapter.submitList(messageList)
        binding.messageText.text?.clear()
        binding.messageItems.scrollToPosition(messageList.size - 1)
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val currentTime = Date()
        return "Today, ${timeFormat.format(currentTime)}"
    }
}