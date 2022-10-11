package study.seo.a2userinputproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var quantity = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun displayQuantity(number: Int = 0) {
        val quantityView = findViewById<TextView>(R.id.quantityNum)
        quantityView.text = number.toString()
    }
    fun increment(view: View) {
        displayQuantity(++quantity)
    }

    fun decrement(view: View) {
        if (quantity > 0) {
            displayQuantity(--quantity)
        } else {
            Toast.makeText(this, "수량이 이미 0입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun submitOrder(view: View) {
        val name = writeName()
        val cream = isCheckCream()
        val chocolate = isCheckChoco()
        val price = calcPrice(cream, chocolate)
        //priceView.text = summaryOrder(price, cream, chocolate, name)
        summaryOrder(price, cream, chocolate, name)
    }

    private fun writeName(): String {
        val name = findViewById<EditText>(R.id.writeName)
        return name.text.toString()
    }

    private fun summaryOrder(
        price: String,
        whippingCream: String,
        chocolate: String,
        name: String
    ) {
        if (name.isBlank()) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        val finalOrder = """Name : $name
            |CreamAdd? : $whippingCream
            |ChocoAdd? : $chocolate
            |Quantity : $quantity 
            |Total: $${(price)}
                |Thank you!""".trimMargin()

        val sendOrder = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, finalOrder)
            type = "text/plain"
        }

        val order = Intent.createChooser(sendOrder, null)
        startActivity(order)
    }

    private fun isCheckCream(): String {
        val checkCream = findViewById<CheckBox>(R.id.whippingCreamChk)
        return if (checkCream.isChecked) {
            "YES"
        } else {
            "NO"
        }
    }

    private fun isCheckChoco(): String {
        val chocolate = findViewById<CheckBox>(R.id.chocolateChk)
        return if (chocolate.isChecked) {
            "YES"
        } else {
            "NO"
        }
    }

    private fun calcPrice(cream: String, chocolate: String): String {
        var sum = 0
        if (cream == "YES") {
            sum += 1
        }
        if (chocolate == "YES") {
            sum += 2
        }
        return (sum + quantity * 10).toString()
    }


}