package com.example.revisions

import androidx.lifecycle.ViewModel


class ChatViewModel : ViewModel() {

    private val chatItems: List<ChatItem>

    init {
        val jsonString = """
[
    {
        "id": 1,
        "image": "https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg",
        "owner": "გრიშა ონიანი",
        "last_message": "თავის ტერიტორიას ბომბავდა",
        "last_active": "4:20 PM",
        "unread_messages": 3,
        "is_typing": false,
        "last_message_type": "text"
    },
    {
        "id": 2,
        "image": null,
        "owner": "ჯემალ კაკაურიძე",
        "last_message": "შემოგევლე",
        "last_active": "3:00 AM",
        "unread_messages": 0,
        "is_typing": true,
        "last_message_type": "voice"
    },
    {
        "id": 3,
        "image": "https://i.ytimg.com/vi/KYY0TBqTfQg/hqdefault.jpg",
        "owner": "გურამ ჯინორია",
        "last_message": "ცოცხალი ვარ მა რა ვარ შე.. როდის იყო კვტარი ტელეფონზე ლაპარაკობდა",
        "last_active": "2:15 PM",
        "unread_messages": 5,
        "is_typing": false,
        "last_message_type": "file"
    },
    {
        "id": 4,
        "image": "",
        "owner": "კაკო წენგუაშვილი",
        "last_message": "ადამიანი რო მოსაკლავად გაგიმეტებს თანაც ქალი ის დასანდობი არ არი",
        "last_active": "1:00 PM",
        "unread_messages": 2,
        "is_typing": false,
        "last_message_type": "text"
    },
    {
        "id": 5,
        "image": "https://via.placeholder.com/150",
        "owner": "ანი გოგოჭური",
        "last_message": "ვიმუშავე ჩეხეთში ცოტა ხანს",
        "last_active": "Yesterday",
        "unread_messages": 0,
        "is_typing": false,
        "last_message_type": "text"
    },
    {
        "id": 6,
        "image": null,
        "owner": "ნინო არჩვაძე",
        "last_message": "ახლა დაგირეკავ",
        "last_active": "5:30 AM",
        "unread_messages": 1,
        "is_typing": true,
        "last_message_type": "voice"
    },
    {
        "id": 7,
        "image": "https://via.placeholder.com/300",
        "owner": "გიორგი ბეჟაშვილი",
        "last_message": "ფაილი გავგზავნე",
        "last_active": "10:10 PM",
        "unread_messages": 7,
        "is_typing": false,
        "last_message_type": "file"
    },
    {
        "id": 8,
        "image": "https://randomuser.me/api/portraits/men/81.jpg",
        "owner": "მიხეილ შვანგირაძე",
        "last_message": "წარმატებებს გისურვებ!",
        "last_active": "Now",
        "unread_messages": 10,
        "is_typing": false,
        "last_message_type": "text"
    }
]
""".trimIndent()


        val moshi = MoshiProvider.moshi
        val listType = com.squareup.moshi.Types.newParameterizedType(List::class.java, ChatItem::class.java)
        val adapter = moshi.adapter<List<ChatItem>>(listType)

        chatItems = adapter.fromJson(jsonString) ?: emptyList()
    }

    fun getChatItems(): List<ChatItem> = chatItems
}


