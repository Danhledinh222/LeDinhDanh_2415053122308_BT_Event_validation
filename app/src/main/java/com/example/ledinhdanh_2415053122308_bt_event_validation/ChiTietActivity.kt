package com.example.ledinhdanh_2415053122308_bt_event_validation
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import  com.example.ledinhdanh_2415053122308_bt_event_validation.databinding.ActivityChiTietBinding

class ChiTietActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChiTietBinding

    companion object {
        const val EXTRA_HO_TEN = "extra_ho_ten"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_SDT = "extra_sdt"
        const val EXTRA_MSV = "extra_msv"
        const val EXTRA_LOP = "extra_lop"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityChiTietBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInsets()
        showStudentInfo()

        binding.btnVeDangKy.setOnClickListener {
            veDangKy()
        }
    }

    private fun setupInsets() {
        WindowCompat.getInsetsController(window, binding.root).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val safeInsets = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )

            view.setPadding(
                safeInsets.left,
                safeInsets.top,
                safeInsets.right,
                safeInsets.bottom
            )

            insets
        }

        ViewCompat.requestApplyInsets(binding.root)
    }

    private fun showStudentInfo() {
        val hoTen = intent.getStringExtra(EXTRA_HO_TEN).orEmpty()
        val email = intent.getStringExtra(EXTRA_EMAIL).orEmpty()
        val sdt = intent.getStringExtra(EXTRA_SDT).orEmpty()
        val msv = intent.getStringExtra(EXTRA_MSV).orEmpty()
        val lop = intent.getStringExtra(EXTRA_LOP).orEmpty()

        if (listOf(hoTen, email, sdt, msv, lop).any { it.isBlank() }) {
            Toast.makeText(
                this,
                "Chưa có đủ thông tin đăng kí",
                Toast.LENGTH_SHORT
            ).show()

            veDangKy()
            return
        }

        binding.tvHoTen.text = hoTen
        binding.tvEmail.text = email
        binding.tvSdt.text = sdt
        binding.tvMsv.text = msv
        binding.tvLop.text = lop
    }

    private fun veDangKy() {
        val intent = Intent(this, MainActivity::class.java).apply {
            // Trở về Activity đang có để giữ thông tin đã nhập.
            addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
        }

        startActivity(intent)
        finish()
    }
}