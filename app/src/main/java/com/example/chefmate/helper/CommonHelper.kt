package com.example.chefmate.helper

import android.annotation.SuppressLint
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.ShoppingItemEntity
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CommonHelper {

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(Date())
    }

    fun parseName(name: String): String {
        val normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
        val noAccents = normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            .replace('đ', 'd')
            .replace('Đ', 'D')

        return noAccents
            .trim()
            .split("\\s+".toRegex())
            .joinToString("_")
    }

    fun consolidateIngredients(ingredients: List<ShoppingItemEntity>? = null): List<ShoppingItemEntity> {
        val consolidated = mutableListOf<ShoppingItemEntity>()
        ingredients?.let { listIngredient ->
            listIngredient.forEach { ingredient ->
                val exitIngredient = consolidated.find {
                    it.siName == ingredient.siName && it.siUnit == ingredient.siUnit
                }
                if (exitIngredient != null) {
                    var newWeight = exitIngredient.siWeight.toInt() + ingredient.siWeight.toInt()
                    consolidated.remove(exitIngredient)
                    consolidated.add(exitIngredient.copy(siWeight = newWeight.toString()))
                } else {
                    consolidated.add(ingredient)
                }
            }
        }
        return consolidated
    }
}
