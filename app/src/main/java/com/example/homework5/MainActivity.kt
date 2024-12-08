package com.example.homework5

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personInfo = mutableMapOf<String, String>()

        binding.addUser.setOnClickListener {
            binding.fNameError.text = ""
            binding.emailError.text = ""

            val fName = binding.FullName.text.toString().trim()
            val email = binding.email.text.toString().trim()

            if(fName.isEmpty()){
                binding.fNameError.text = "Full Name Section is Empty!"
            }
            if(email.isEmpty()){
                binding.emailError.text= "Email Section is Empty!"
            }else if(!email.contains('@')){
                binding.emailError.text = "Invalid Email Format!"
            }else if(personInfo.containsKey(email)){
                binding.emailError.text = "This Email Address Already Exsists!"
            }else{
                personInfo[email] = fName
                binding.Users.text = "Users : ${personInfo.size}"
                binding.FullName.text.clear()
            }
            binding.email.text.clear()
        }
        binding.userInfo.setOnClickListener {
            binding.infoError.text = ""
            val checkEmail = binding.searchByEmail.text.toString()

            if(checkEmail.isEmpty()){
                binding.infoError.text = "Email Section is Empty!"
            }else if(!checkEmail.contains('@')){
                binding.infoError.text = "Invalid Email Format!"
            }else if(!personInfo.containsKey(checkEmail)){
                binding.infoError.text = "Such Email Doesnt Exsists!"
            }else{
                binding.linearLayout.visibility = View.GONE
                binding.addUser.visibility = View.GONE
                binding.personInfo.text = """
                    
                    Full Name : ${personInfo[checkEmail]}
                    
                    Email Address: $checkEmail
                    
                    """.trimIndent()

            }
            binding.searchByEmail.text.clear()
        }

    }
}