package com.vfg.silkroad.goo.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

sealed interface FormulaToken {
    val kind: String

    data class Element(val symbol: String, val subscript: Int = 1) : FormulaToken {
        override val kind: String get() = "el"
    }
    data class Charge(val value: Int) : FormulaToken {
        override val kind: String get() = "ch"
    }
    data object Plus : FormulaToken {
        override val kind: String get() = "pl"
    }
    data object Arrow : FormulaToken {
        override val kind: String get() = "ar"
    }
}

data class FormulaEntry(
    val id: String,
    val tokens: List<FormulaToken>,
    val note: String,
    val createdAt: Long,
) {
    companion object {
        fun create(tokens: List<FormulaToken>, note: String): FormulaEntry =
            FormulaEntry(
                id = UUID.randomUUID().toString(),
                tokens = tokens,
                note = note,
                createdAt = System.currentTimeMillis()
            )
    }
}

private const val PREFS_NAME = "atom_journey_prefs"
private const val KEY_NOTES = "lab_notes_v1"

class NotesRepository private constructor(private val prefs: SharedPreferences) {

    private val _entries: SnapshotStateList<FormulaEntry> = mutableStateListOf<FormulaEntry>().apply {
        addAll(load())
    }
    val entries: List<FormulaEntry> get() = _entries

    fun save(entry: FormulaEntry) {
        _entries.add(0, entry)
        persist()
    }

    fun delete(id: String) {
        _entries.removeAll { it.id == id }
        persist()
    }

    private fun persist() {
        val arr = JSONArray()
        _entries.forEach { e ->
            val o = JSONObject()
            o.put("id", e.id)
            o.put("note", e.note)
            o.put("at", e.createdAt)
            val toks = JSONArray()
            e.tokens.forEach { t ->
                val jt = JSONObject()
                jt.put("k", t.kind)
                when (t) {
                    is FormulaToken.Element -> {
                        jt.put("s", t.symbol)
                        jt.put("n", t.subscript)
                    }
                    is FormulaToken.Charge -> jt.put("v", t.value)
                    FormulaToken.Plus, FormulaToken.Arrow -> {}
                }
                toks.put(jt)
            }
            o.put("toks", toks)
            arr.put(o)
        }
        prefs.edit().putString(KEY_NOTES, arr.toString()).apply()
    }

    private fun load(): List<FormulaEntry> {
        val raw = prefs.getString(KEY_NOTES, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            (0 until arr.length()).map { i ->
                val o = arr.getJSONObject(i)
                val toksJson = o.getJSONArray("toks")
                val toks = (0 until toksJson.length()).map { j ->
                    val jt = toksJson.getJSONObject(j)
                    when (jt.getString("k")) {
                        "el" -> FormulaToken.Element(jt.getString("s"), jt.getInt("n"))
                        "ch" -> FormulaToken.Charge(jt.getInt("v"))
                        "pl" -> FormulaToken.Plus
                        "ar" -> FormulaToken.Arrow
                        else -> error("unknown token kind")
                    }
                }
                FormulaEntry(
                    id = o.getString("id"),
                    tokens = toks,
                    note = o.getString("note"),
                    createdAt = o.getLong("at"),
                )
            }
        }.getOrDefault(emptyList())
    }

    companion object {
        @Volatile private var instance: NotesRepository? = null
        fun get(context: Context): NotesRepository =
            instance ?: synchronized(this) {
                instance ?: NotesRepository(
                    context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                ).also { instance = it }
            }
    }
}
