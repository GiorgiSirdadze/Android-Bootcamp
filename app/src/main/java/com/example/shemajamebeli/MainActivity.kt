package com.example.shemajamebeli

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shemajamebeli.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    val inputs = mutableListOf<String>()
    val anagrams = anagramFinder(inputs)

    val anagramText = anagrams.joinToString(", ")  {it}


        binding.saveButton.setOnClickListener {

            val anagram = binding.anagrams.text.toString().trim()
            inputs.add(anagram)

        }

        binding.outputButton.setOnClickListener {
         binding.linearLayout.visibility = View.GONE

            binding.anagrams.text = """
             
             
          Anagrams:  $anagramText
             
             
             
             
         """.trimIndent()


        }
        binding.clearButton.setOnClickListener {
            binding.anagrams.visibility = View.GONE
            inputs.clear()
            binding.linearLayout.visibility = View.VISIBLE
        }




    }

    private fun anagramFinder(strings: List<String>): List<String>{
         val result = mutableListOf<String>()

         for (index in strings.indices){
           val firstWord = strings[index]
           val sortedWord1 = firstWord.toCharArray().sorted().joinToString("")

           for(index2 in strings.indices){
               if(index != index2){
                   val secondWord = strings[index2]
                   val sortedWord2 = secondWord.toCharArray().sorted().joinToString("")

                   if(sortedWord1 == sortedWord2){
                       if(!result.contains(firstWord)){
                           result.add(firstWord)
                       }
                       if (!result.contains(secondWord)){
                           result.add(secondWord)
                       }
                   }
               }
           }

         }
        return result
    }



}