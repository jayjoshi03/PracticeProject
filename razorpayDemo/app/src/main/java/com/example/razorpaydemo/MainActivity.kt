package com.example.razorpaydemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.razorpaydemo.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPay.setOnClickListener {
            binding.apply {
                val payAmount = textPay.text.toString()
                val amount = (payAmount.toFloat() * 100).roundToInt()

                val checkout = Checkout()
                checkout.setKeyID("rzp_test_TtHAxaCGBPxq5p")
                checkout.setImage(R.drawable.ic_launcher_foreground)

                val objectJson = JSONObject()
                try {
                    objectJson.put("name", "Testing")
                    objectJson.put("description", "T-shirt")
                    objectJson.put("theme.color", "")
                    objectJson.put("currency", "INR")
                    objectJson.put("amount", amount)
                    objectJson.put("prefill.contact", "8849728739")
                    objectJson.put("prefill.email", "jayjoshi1536@gmail.com")

                    checkout.open(this@MainActivity, objectJson)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onPaymentSuccess(msg : String?) {
        Toast.makeText(this, "Payment Success : $msg", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(i : Int, msg : String?) {
        Toast.makeText(this, "Payment Fail due to Error : $msg", Toast.LENGTH_SHORT).show()
    }
}