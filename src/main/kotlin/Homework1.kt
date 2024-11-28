fun main(){
    while(true) {
        println("შეიყვანეთ X ის მნიშვნელობა: ")
        val x = readln()
        println("შეიყვანეთ Y ის მნიშვნელობა (იგი აუცილებლად უნდა შეიცავდეს სულ მცირე ერთ 0 ისაგან განსხვავებულ ციფრს): ")
        val y = readln()

        var newX: String = ""
        var newY: String = ""

        for (num in x) {
            if (num.isDigit()) {
                newX += num
            }
        }
        for (num in y) {
            if (num.isDigit()) {
                newY += num
            }
        }
        if (newX.isEmpty()) {
            newX += "0"
        }
        if(newY.isEmpty()){
            println("არასწორი Y ის მნიშვნელობა!")
            break
        }
        val xValue = newX.toDouble()
        val yValue = newY.toDouble()

        val zValue = xValue / yValue

        println("X ის და Y ის განაყოფი უდრის: $zValue")

        println("გსურთ პროგრამის ხელახლა დაწყება <Y/N>?")

        val answer = readln()

        if(answer == "N"){
            break
        }else{
            continue
        }
    }
}