package com.example.shemajamebeli2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shemajamebeli2.databinding.ActivityMainBinding

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val address: String,
    val email: String
)

class MainActivity : AppCompatActivity() {

   private val users = mutableListOf(
        User(
            id = 1,
            firstName = "გრიშა",
            lastName = "ონიანი",
            birthday = "1724647601641",
            address = "სტალინის სახლმუზეუმი",
            email = "grisha@mail.ru"
        ),
        User(
            id = 2,
            firstName = "Jemal",
            lastName = "Kakauridze",
            birthday = "1714647601641",
            address = "თბილისი, ლილოს მიტოვებული ქარხანა",
            email = "jemal@gmail.com"
        ),
        User(
            id = 2,
            firstName = "Omger",
            lastName = "Kakauridze",
            birthday = "1724647701641",
            address = "თბილისი, ასათიანი 18",
            email = "omger@gmail.com"
        ),
        User(
            id = 32,
            firstName = "ბორის",
            lastName = "გარუჩავა",
            birthday = "1714947701641",
            address = "თბილისი, იაშვილი 14",
            email = ""
        ),
        User(
            id = 34,
            firstName = "აბთო",
            lastName = "სიხარულიძე",
            birthday = "1711947701641",
            address = "ფოთი",
            email = "tebzi@gmail.com",
        )
    )
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchButton.setOnClickListener {
            val identifier = binding.searchField.text.toString().trim()

            val foundedUser = searchUser(users, identifier)


            if (foundedUser != null) {

                binding.personInfo.text = """
                    ID: ${foundedUser.id}
                    First Name: ${foundedUser.firstName}
                    Last Name: ${foundedUser.lastName}
                    Birthday: ${foundedUser.birthday}
                    Address: ${foundedUser.address}
                    Email: ${foundedUser.email}
                """.trimIndent()
            }else{
                Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show()
                binding.addUser.visibility = View.VISIBLE
            }

        }

        binding.addUser.setOnClickListener {
            binding.addUser.visibility = View.GONE
            binding.searchField.visibility = View.GONE
            binding.searchButton.visibility = View.GONE
            binding.fields.visibility = View.VISIBLE
            binding.saveButton.visibility = View.VISIBLE
            addUser()
        }


    }


    private fun searchUser(users: List<User>, identifier: String): User? {
        return users
            .filter { user ->
                identifier in user.id.toString() ||
                        identifier in user.firstName ||
                        identifier in user.lastName ||
                        identifier in user.birthday ||
                        identifier in user.address ||
                        identifier in user.email
            }
            .minByOrNull { it.id } // so if we have some mathing propery we get info whichs id is less
    }

    private fun addUser(){
        val idTxt = binding.id.text.toString().trim()
        val id = idTxt.toIntOrNull()
        val fName = binding.firstname.text.toString().trim()
        val lName = binding.lastname.text.toString().trim()
        val bDay = binding.birthday.text.toString().trim()
        val address = binding.address.text.toString().trim()
        val email = binding.email.text.toString().trim()

        if (fName.isEmpty() || lName.isEmpty() || bDay.isEmpty() || address.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show()
            return
        }

        if (id == null) {
            Toast.makeText(this, "Please enter a valid numeric value for ID", Toast.LENGTH_SHORT).show()
            return
        }

        val newUser = User(
            id = id,
            firstName = fName,
            lastName = lName,
            birthday = bDay,
            address = address,
            email = email
        )
        users.add(newUser)

        binding.searchField.visibility = View.VISIBLE
        binding.searchButton.visibility = View.VISIBLE
        binding.fields.visibility = View.GONE
        binding.saveButton.visibility = View.GONE
    }
}