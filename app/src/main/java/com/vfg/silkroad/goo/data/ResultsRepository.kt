package com.vfg.silkroad.goo.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.json.JSONArray
import org.json.JSONObject

data class TestResult(
    val topicId: String,
    val correct: Int,
    val total: Int,
    val timeSpentSeconds: Int,
    val completedAt: Long,
) {
    val percent: Int get() = if (total == 0) 0 else (correct * 100 / total)
}

private const val PREFS_NAME = "atom_journey_prefs"
private const val KEY_RESULTS = "results_v1"

class ResultsRepository private constructor(private val prefs: SharedPreferences) {

    private val _results: SnapshotStateList<TestResult> = mutableStateListOf<TestResult>().apply {
        addAll(load())
    }
    val results: List<TestResult> get() = _results

    fun save(result: TestResult) {
        _results.add(result)
        persist()
    }

    fun clear() {
        _results.clear()
        prefs.edit().remove(KEY_RESULTS).apply()
    }

    fun bestFor(topicId: String): TestResult? =
        _results.filter { it.topicId == topicId }.maxByOrNull { it.percent }

    fun latestFor(topicId: String): TestResult? =
        _results.filter { it.topicId == topicId }.maxByOrNull { it.completedAt }

    fun averagePercent(): Int {
        if (_results.isEmpty()) return 0
        return _results.sumOf { it.percent } / _results.size
    }

    fun bestTopicId(): String? =
        _results.groupBy { it.topicId }
            .mapValues { (_, list) -> list.maxOf { it.percent } }
            .maxByOrNull { it.value }
            ?.key

    fun weakestTopicId(): String? =
        _results.groupBy { it.topicId }
            .mapValues { (_, list) -> list.maxOf { it.percent } }
            .minByOrNull { it.value }
            ?.key

    private fun persist() {
        val arr = JSONArray()
        _results.forEach { r ->
            val o = JSONObject()
            o.put("topic", r.topicId)
            o.put("correct", r.correct)
            o.put("total", r.total)
            o.put("time", r.timeSpentSeconds)
            o.put("at", r.completedAt)
            arr.put(o)
        }
        prefs.edit().putString(KEY_RESULTS, arr.toString()).apply()
    }

    private fun load(): List<TestResult> {
        val raw = prefs.getString(KEY_RESULTS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            (0 until arr.length()).map { i ->
                val o = arr.getJSONObject(i)
                TestResult(
                    topicId = o.getString("topic"),
                    correct = o.getInt("correct"),
                    total = o.getInt("total"),
                    timeSpentSeconds = o.getInt("time"),
                    completedAt = o.getLong("at")
                )
            }
        }.getOrDefault(emptyList())
    }

    companion object {
        @Volatile private var instance: ResultsRepository? = null
        fun get(context: Context): ResultsRepository =
            instance ?: synchronized(this) {
                instance ?: ResultsRepository(
                    context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                ).also { instance = it }
            }
    }
}
