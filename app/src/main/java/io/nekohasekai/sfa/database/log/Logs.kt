package io.nekohasekai.sfa.database.log

import androidx.room.Room
import io.nekohasekai.sfa.Application
import io.nekohasekai.sfa.constant.LogKey
import io.nekohasekai.sfa.constant.Path
import io.nekohasekai.sfa.database.preference.KeyValueDatabase
import io.nekohasekai.sfa.database.preference.RoomPreferenceDataStore
import io.nekohasekai.sfa.ktx.boolean
import io.nekohasekai.sfa.ktx.string
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Logs {

    @OptIn(DelicateCoroutinesApi::class)
    private val instance by lazy {
        Application.application.getDatabasePath(Path.LOG_DATABASE_PATH).parentFile?.mkdirs()
        Room.databaseBuilder(
            Application.application,
            KeyValueDatabase::class.java,
            Path.LOG_DATABASE_PATH
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration(false)
            .enableMultiInstanceInvalidation()
            .setQueryExecutor { GlobalScope.launch { it.run() } }
            .build()
    }

    val dataStore = RoomPreferenceDataStore(Logs.instance.keyValuePairDao())

    var disabled by dataStore.boolean(LogKey.DISABLED) { false }
    var level by dataStore.string(LogKey.LEVEL) { "error" }
    var timestamp by dataStore.boolean(LogKey.TIMESTAMP) { true }
}