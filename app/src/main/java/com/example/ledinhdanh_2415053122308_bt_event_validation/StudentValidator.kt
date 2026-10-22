package com.example.ledinhdanh_2415053122308_bt_event_validation

import android.util.Patterns

object StudentValidator {

    private val nameRegex =
        Regex("^[\\p{L}\\p{M}]+(?:[ '\\-][\\p{L}\\p{M}]+)*$")

    fun cleanText(value: String): String {
        return value.trim().replace(Regex("\\s+"), " ")
    }

    fun hoTenError(value: String): String? {
        val name = cleanText(value)

        return when {
            name.isEmpty() ->
                "Vui lòng nhập họ và tên"

            name.length !in 2..80 ->
                "Họ tên phải từ 2 đến 80 ký tự"

            !nameRegex.matches(name) ->
                "Họ tên chỉ gồm chữ, khoảng trắng, dấu ' hoặc -"

            else -> null
        }
    }

    fun emailError(value: String): String? {
        val email = value.trim()

        return when {
            email.isEmpty() ->
                "Vui lòng nhập email"

            email.length > 254 ->
                "Email quá dài"

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Email không hợp lệ, ví dụ: danh@gmail.com"

            else -> null
        }
    }
    private val phoneRegex = Regex("^0[35789][0-9]{8}$")
    private val studentIdRegex = Regex("^[0-9]+$")
    private val classRegex = Regex("^[\\p{L}\\p{M}0-9 ._/-]+$")

    fun normalizePhone(value: String): String {
        val phone = value.trim().replace(Regex("[\\s-]"), "")

        return if (phone.startsWith("+84")) {
            "0" + phone.substring(3)
        } else {
            phone
        }
    }

    fun sdtError(value: String): String? {
        return when {
            value.isBlank() ->
                "Vui lòng nhập số điện thoại"

            !phoneRegex.matches(normalizePhone(value)) ->
                "SĐT di động không hợp lệ, ví dụ: 0901234567"

            else -> null
        }
    }

    fun msvError(value: String): String? {
        val msv = value.trim()

        return when {
            msv.isEmpty() ->
                "Vui lòng nhập mã sinh viên"

            !studentIdRegex.matches(msv) ->
                "Mã sinh viên chỉ được chứa chữ số"

            else -> null
        }
    }

    fun lopError(value: String): String? {
        val lop = cleanText(value)

        return when {
            lop.isEmpty() ->
                "Vui lòng nhập lớp"

            !classRegex.matches(lop) ->
                "Lớp chỉ gồm chữ, số, khoảng trắng và . _ / -"

            !lop.any { it.isLetter() } ->
                "Lớp phải có ít nhất một chữ cái"

            else -> null
        }
    }

}