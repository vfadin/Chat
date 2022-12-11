package com.vfadin.events.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPrefs(
    private val context: Context
) {
    fun saveToken(token: String, tokenType: String, expirationDate: Long) {
        EncryptedSharedPreferences.create(
            context,
            "encrypted_token",
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ).edit().apply {
            putString("token", token)
            putString("token_type", tokenType)
            putLong("expiration_date", expirationDate)
            apply()
        }
    }

    fun restoreToken(): String? {
        return EncryptedSharedPreferences.create(
            context,
            "encrypted_token",
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ).getString("token", null)
    }

    fun restoreTokenType(): String? {
        return EncryptedSharedPreferences.create(
            context,
            "encrypted_token",
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ).getString("token_type", null)
    }

    fun restoreExpirationDate(): Long {
        return EncryptedSharedPreferences.create(
            context,
            "encrypted_token",
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ).getLong("expiration_date", Long.MIN_VALUE)
    }

    fun clear() {
        EncryptedSharedPreferences.create(
            context,
            "encrypted_token",
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ).edit().clear().apply()
    }
}