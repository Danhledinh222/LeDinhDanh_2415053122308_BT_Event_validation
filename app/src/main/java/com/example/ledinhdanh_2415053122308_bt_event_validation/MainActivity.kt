package com.example.ledinhdanh_2415053122308_bt_event_validation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import android.content.Intent
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.ledinhdanh_2415053122308_bt_event_validation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private data class Field(
        val layout: TextInputLayout,
        val input: TextInputEditText,
        val validator: (String) -> String?
    )
    private lateinit var fields: List<Field>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInsets()
        setupForm()
    }

    override fun onResume() {
        super.onResume()

        if (::binding.isInitialized) {
            binding.btnDangKy.isEnabled = true
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
                        WindowInsetsCompat.Type.displayCutout() or
                        WindowInsetsCompat.Type.ime()
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

    private fun setupForm() {
        fields = listOf(
            Field(binding.tilHoTen, binding.edtHoTen, StudentValidator::hoTenError),
            Field(binding.tilEmail, binding.edtEmail, StudentValidator::emailError),
            Field(binding.tilSdt, binding.edtSdt, StudentValidator::sdtError),
            Field(binding.tilMsv, binding.edtMsv, StudentValidator::msvError),
            Field(binding.tilLop, binding.edtLop, StudentValidator::lopError)
        )

        binding.btnDangKy.setOnClickListener {
            submitRegistration()
        }
    }

    private fun validateField(field: Field): Boolean {
        val errorMessage = field.validator(field.input.text?.toString().orEmpty())
        field.layout.error = errorMessage
        return errorMessage == null
    }

    private fun submitRegistration() {
        val results = fields.map { validateField(it) }
        val firstInvalidIndex = results.indexOfFirst { !it }

        if (firstInvalidIndex != -1) {
            fields[firstInvalidIndex].input.requestFocus()
            Toast.makeText(this, "Vui lòng kiểm tra các trường đang báo lỗi", Toast.LENGTH_SHORT).show()
            return
        }

        onFormValid()
    }

    private fun onFormValid() {
        if (!binding.btnDangKy.isEnabled) return

        val hoTen = StudentValidator.cleanText(binding.edtHoTen.text?.toString().orEmpty())
        val email = binding.edtEmail.text?.toString().orEmpty().trim()
        val sdt = StudentValidator.normalizePhone(binding.edtSdt.text?.toString().orEmpty())
        val msv = binding.edtMsv.text?.toString().orEmpty().trim()
        val lop = StudentValidator.cleanText(binding.edtLop.text?.toString().orEmpty())

        binding.btnDangKy.isEnabled = false

        WindowCompat.getInsetsController(window, binding.root).hide(WindowInsetsCompat.Type.ime())

        val intent = Intent(this, ChiTietActivity::class.java).apply {
            putExtra(ChiTietActivity.EXTRA_HO_TEN, hoTen)
            putExtra(ChiTietActivity.EXTRA_EMAIL, email)
            putExtra(ChiTietActivity.EXTRA_SDT, sdt)
            putExtra(ChiTietActivity.EXTRA_MSV, msv)
            putExtra(ChiTietActivity.EXTRA_LOP, lop)
        }

        startActivity(intent)
    }

}