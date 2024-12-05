package com.example.homework4

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val editEmail : EditText = findViewById(R.id.email)
        val editUsername : EditText = findViewById(R.id.username)
        val editFirstName : EditText = findViewById(R.id.firstname)
        val editLastName : EditText = findViewById(R.id.lastname)
        val editAge : EditText = findViewById(R.id.age)
        val editPhoneNumber : EditText = findViewById(R.id.phone_number)
        val saveButton : Button = findViewById(R.id.save)
        val clearButton : Button = findViewById(R.id.clear)
        val emailError =  findViewById<TextView>(R.id.emailError)
        val usernameError =  findViewById<TextView>(R.id.usernameError)
        val fNameError =  findViewById<TextView>(R.id.fNameError)
        val lNameError =  findViewById<TextView>(R.id.lNameError)
        val ageError =  findViewById<TextView>(R.id.ageError)
        val pNumberError =  findViewById<TextView>(R.id.pNumberError)
        val personInfo =  findViewById<TextView>(R.id.personInfo)
        val againButton : Button = findViewById(R.id.again)

        againButton.visibility = Button.GONE

        saveButton.setOnClickListener {
            emailError.text = ""
            usernameError.text = ""
            fNameError.text = ""
            lNameError.text = ""
            ageError.text = ""
            pNumberError.text = ""

            val email = editEmail.text.toString().trim()
            val username = editUsername.text.toString().trim()
            val firstName = editFirstName.text.toString().trim()
            val lastName = editLastName.text.toString().trim()
            val age = editAge.text.toString()
            val phoneNumber = editPhoneNumber.text.toString().trim()

            if(email.isEmpty()){
              emailError.text = "Email Section is Empty!"
            }else if(!email.contains('@')){
                emailError.text = "Invalid Email Format!"
            }
            if(username.isEmpty()){
                usernameError.text = "Username Section is Empty!"
            }else if(username.length < 10){
                usernameError.text = "UserName Must Contain min 10 Character"
            }
            if(firstName.isEmpty()){
                fNameError.text = "FirstName Section is Empty!"
            }
            if(lastName.isEmpty()){
                lNameError.text = "LastName Section is Empty!"
            }
            if(age.isEmpty()){
                ageError.text = "Age Section is Empty!"
            }
            if(phoneNumber.isEmpty()){
                pNumberError.text = "Phone Number Section is Empty!"
            }




            if(email.isNotEmpty() && username.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty() && phoneNumber.isNotEmpty()){
                editEmail.visibility = EditText.GONE
                editUsername.visibility = EditText.GONE
                editFirstName.visibility = EditText.GONE
                editLastName.visibility = EditText.GONE
                editAge.visibility = EditText.GONE
                editPhoneNumber.visibility = EditText.GONE
                emailError.visibility = TextView.GONE
                usernameError.visibility = TextView.GONE
                fNameError.visibility = TextView.GONE
                lNameError.visibility = TextView.GONE
                ageError.visibility = TextView.GONE
                pNumberError.visibility = TextView.GONE
                saveButton.visibility = Button.GONE
                clearButton.visibility = Button.GONE

                againButton.visibility = Button.VISIBLE

                personInfo.text = """
                    
                    Email: $email
                    
                    Username: $username
                    
                    Full Name: $firstName $lastName
                    
                    Age: $age
                    
                    Number: $phoneNumber
                """.trimIndent()

                againButton.setOnClickListener {
                    editEmail.visibility = EditText.VISIBLE
                    editUsername.visibility = EditText.VISIBLE
                    editFirstName.visibility = EditText.VISIBLE
                    editLastName.visibility = EditText.VISIBLE
                    editAge.visibility = EditText.VISIBLE
                    editPhoneNumber.visibility = EditText.VISIBLE
                    emailError.visibility = TextView.VISIBLE
                    usernameError.visibility = TextView.VISIBLE
                    fNameError.visibility = TextView.VISIBLE
                    lNameError.visibility = TextView.VISIBLE
                    ageError.visibility = TextView.VISIBLE
                    pNumberError.visibility = TextView.VISIBLE
                    saveButton.visibility = Button.VISIBLE
                    clearButton.visibility = Button.VISIBLE
                    againButton.visibility = Button.GONE
                    personInfo.text = ""

                    editEmail.text.clear()
                    editUsername.text.clear()
                    editFirstName.text.clear()
                    editLastName.text.clear()
                    editAge.text.clear()
                    editPhoneNumber.text.clear()
                }
            }
        }
        clearButton.setOnLongClickListener {
            editEmail.text.clear()
            editUsername.text.clear()
            editFirstName.text.clear()
            editLastName.text.clear()
            editAge.text.clear()
            editPhoneNumber.text.clear()

            emailError.text = ""
            usernameError.text = ""
            fNameError.text = ""
            lNameError.text = ""
            ageError.text = ""
            pNumberError.text = ""

            true
        }
    }
}