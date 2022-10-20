package study.seo.a2userinputproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import study.seo.a2userinputproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var quantity = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plusButton.setOnClickListener {
            displayQuantity(++quantity)
        }

        binding.minusButton.setOnClickListener {
            if (quantity > 0) {
                displayQuantity(--quantity)
            } else {
                Toast.makeText(this, "수량이 이미 0입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.orderButton.setOnClickListener {
            val cream = binding.whippingCreamChk.isChecked
            val choco = binding.chocolateChk.isChecked
            summaryOrder(calcPrice(cream, choco), cream, choco, binding.writeName.text.toString())
        }
    }

    private fun displayQuantity(number: Int = 0) {
        binding.quantityNum.text = number.toString()
    }

    private fun summaryOrder(
        price: String,
        whippingCream: Boolean,      //Boolean값으로 바꾸고 간단히 쓸수 있을듯
        chocolate: Boolean,
        name: String
    ) {
        if (name.isBlank()) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }

        val finalOrder = """Name : $name
            |CreamAdd? : ${whippingCream.toOrder()}
            |ChocoAdd? : ${chocolate.toOrder()}
            |Quantity : $quantity 
            |Total: $${(price)}
                |Thank you!""".trimMargin()

        val sendOrder = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, finalOrder)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(sendOrder, null))
    }

    private fun calcPrice(cream: Boolean, chocolate: Boolean): String {
        val sum = if (cream) {
            1
        } else {
            0
        } + if (chocolate) {
            2
        } else {
            0
        }
        return (sum + quantity * 10).toString()
    }


    private fun Boolean.toOrder() = if (this) "YES" else "NO"

}