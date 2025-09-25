package io.nekohasekai.sfa.constant

import android.content.Context
import io.nekohasekai.sfa.R
import io.nekohasekai.sfa.constant.PerAppProxyUpdateType.Deselect
import io.nekohasekai.sfa.constant.PerAppProxyUpdateType.Disabled
import io.nekohasekai.sfa.constant.PerAppProxyUpdateType.Select
import io.nekohasekai.sfa.database.Settings

enum class LogLevel(
    val level: String
) {
    Trace("trace"), Debug("debug"), Info("info"),
    Warn("warn"), Error("error"), Fatal("fatal"),
    Panic("panic");


    companion object {
        fun levelOf(value: String): LogLevel {
            for (level in entries) {
                if (value == level.level){
                    return level
                }
            }
            return Info
        }

    }
}