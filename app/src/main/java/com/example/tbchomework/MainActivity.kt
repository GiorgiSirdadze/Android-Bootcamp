package com.example.tbchomework

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val editTextNumber: EditText = findViewById(R.id.editTextNumber)
        val convertButton: Button = findViewById(R.id.convertButton)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val languageToggle: Switch = findViewById(R.id.languageToggle)


        convertButton.setOnClickListener {
            val numberInput = editTextNumber.text.toString()
            if (numberInput.isNotEmpty()) {
                val number = numberInput.toInt()
                if (number in 1..1000) {
                    val result = if (languageToggle.isChecked) {
                        numberToEnglishName(number)
                    } else {
                        numberToGeorgianName(number)
                    }
                    resultTextView.text = result
                } else {
                    resultTextView.text = "Please enter a number between 1 and 1000."
                }
            } else {
                resultTextView.text = "Please enter a number."
            }
        }
    }
    private fun numberToGeorgianName(number: Int): String {
        // Lists as provided
        val erteulebi =
            listOf("ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა")
        val konkretulebi = listOf(
            "თერთმეტი",
            "თორმეტი",
            "ცამეტი",
            "თოთხმეტი",
            "თხუთმეტი",
            "თექვსმეტი",
            "ჩვიდმეტი",
            "თვრამეტი",
            "ცხრამეტი"
        )
        val ateulebi = listOf(
            "ათი",
            "ოცი",
            "ოცდაათი",
            "ორმოცი",
            "ორმოცდაათი",
            "სამოცი",
            "სამოცდაათი",
            "ოთხმოცი",
            "ოთხმოცდაათი",
            "ასი",
            "ორასი",
            "სამასი",
            "ოთხასი",
            "ხუთასი",
            "ექვსასი",
            "შვიდასი",
            "რვაასი",
            "ცხრაასი"
        )
        val values = listOf("ოცდა", "ორმოცდა", "სამოცდა", "ოთხმოცდა")
        val aseulebi =
            listOf("ას", "ორას", "სამას", "ოთხას", "ხუთას", "ექვსას", "შვიდას", "რვაას", "ცხრაას")


        if (number in 1..9) return erteulebi[number - 1]

        if (number in 10..90 step 10) {
            return ateulebi[number / 10 - 1]
        }
        if (number in 100..900 step 100) {
            return ateulebi[number / 100 + 8]
        }


        if (number in 11..19) return konkretulebi[number - 11]


        if (number in 21..39 || number in 41..59 || number in 61..79 || number in 81..99) {
            val firstDigit = number / 10
            val newDigit = number / 20 - 1
            val secondDigit = number % 10
            return if (firstDigit % 2 == 0) {

                values[newDigit] + erteulebi[secondDigit - 1]
            } else {

                values[newDigit] + konkretulebi[secondDigit - 1]
            }
        }


        if (number in 100..999) {
            val firstDigit = number / 100
            val secondAndThirdDigits = number % 100
            val hundredsPart = aseulebi[firstDigit - 1]


            val remainingPart =
                numberToGeorgianName(secondAndThirdDigits) // rekursiulad darchenil 2 nishna ricxvistvis vabrunebt saxels

            return hundredsPart + remainingPart
        }


        if (number == 1000) return "ათასი"


        return ""
    }
    private fun numberToEnglishName(number: Int): String {
        val digits = listOf(
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
        )
        val teens = listOf(
            "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventeen", "eighteen", "nineteen"
        )
        val tens = listOf(
            "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
        )
        if(number in 1..9) return digits[number - 1]

        if(number in 11..19) return teens[number - 11]
        if (number in 10..90 step 10){
            return tens[number / 10 - 1]
        }
        if (number in 100..900 step 100){
            val num = number / 100
            return digits[num - 1] + " " + "hundred"
        }


        if (number in 21..39 || number in 41..59 || number in 61..79 || number in 81..99){

            val num = number / 10
            val secDigit = number % 10

            return tens[num - 1] + " " + digits[secDigit - 1]

        }
        if (number in 100..999){
            val num = number / 100
            val secondAndThirdDigits = number % 100

            val hundredsPart = digits[num - 1] + " " + "hundred"

            val remainingPart =
                numberToEnglishName(secondAndThirdDigits)

            return "$hundredsPart $remainingPart"
        }
        if (number == 1000) return "One Thousand"

        return ""
    }

}
