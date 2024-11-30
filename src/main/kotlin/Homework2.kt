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

fun checker(str : String){

}
