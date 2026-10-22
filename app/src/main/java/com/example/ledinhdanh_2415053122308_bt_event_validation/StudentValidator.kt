package com.example.bt_event_validation

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
}