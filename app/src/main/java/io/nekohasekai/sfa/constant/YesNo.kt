package io.nekohasekai.sfa.constant

import android.content.Context
import io.nekohasekai.sfa.R
import io.nekohasekai.sfa.constant.PerAppProxyUpdateType.Deselect
import io.nekohasekai.sfa.constant.PerAppProxyUpdateType.Disabled
import io.nekohasekai.sfa.constant.PerAppProxyUpdateType.Select
import io.nekohasekai.sfa.database.Settings

enum class YesNo {
    Yes, No;

    fun value() = when (this) {
        Yes -> true
        No -> false
    }

    fun getString(context: Context): String {
        return when (this) {
            Yes -> context.getString(R.string.yes)
            No -> context.getString(R.string.no)
        }
    }


    companion object {
        fun valueOf(value: Boolean): YesNo = when (value) {
            true -> Yes
            false -> No
        }

        fun valueOf(context: Context, value: String): YesNo {
            return when (value) {
                context.getString(R.string.yes) -> Yes
                context.getString(R.string.no) -> No
                else ->  throw IllegalArgumentException()
            }
        }
    }
}