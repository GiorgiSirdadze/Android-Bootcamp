fun findGCD(x: Int, y: Int): Int {
    var num1 = x
    var num2 = y
    while (num1 != num2) {
        if (num1 < num2) {
            num2 -= num1
        } else {
            num1 -= num2
        }
    }
      return num1
}

fun findLCM(x: Int, y: Int): Int {
    return (x * y) / findGCD(x, y)
}

fun checker(str : String):Boolean{
    for(i in str.indices){
      if(str[i]== '$'){
          return true
      }
  }
    return false
}

fun evenSum(num : Int):Int{
    if(num <= 0){
        return 0
    }else if(num % 2 == 1){
        return evenSum(num - 1)
    }
      return num + evenSum(num - 2 )
    }
fun intReverse(num : Int):Int{
     var n = num
     var final = 0

     while(n > 0){
        final = final * 10 + n % 10
        n /= 10
     }
       return final
}

fun isPalindrome(str : String): Boolean {
    var reversed = ""
    val lastIndice : Int = str.length - 1

    for (i in lastIndice downTo 0){
        reversed += str[i]
    }
    return reversed == str
}


fun main(){
    println(findGCD(9, 21))
    println(findLCM(4,6))
    println(checker("k2msdk$"))
    println(evenSum(100))
    println(intReverse(10220))
    println(isPalindrome("abba"))

}
