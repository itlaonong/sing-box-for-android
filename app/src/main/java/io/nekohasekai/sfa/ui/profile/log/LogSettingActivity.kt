package io.nekohasekai.sfa.ui.profile.log

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import io.nekohasekai.sfa.R
import io.nekohasekai.sfa.constant.LogLevel
import io.nekohasekai.sfa.constant.PerAppProxyUpdateType
import io.nekohasekai.sfa.constant.YesNo
import io.nekohasekai.sfa.database.Settings
import io.nekohasekai.sfa.database.log.Logs
import io.nekohasekai.sfa.databinding.ActivityLogSettingBinding
import io.nekohasekai.sfa.ktx.addTextChangedListener
import io.nekohasekai.sfa.ktx.setSimpleItems
import io.nekohasekai.sfa.ktx.text
import io.nekohasekai.sfa.ui.shared.AbstractActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogSettingActivity : AbstractActivity<ActivityLogSettingBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle("日志配置")
        binding.logLevel.isEnabled = !Logs.disabled
        binding.logTimestamp.isEnabled = !Logs.disabled

        binding.logDisabled.addTextChangedListener {
            val v = !YesNo.valueOf(this@LogSettingActivity, it).value()
            binding.logLevel.isEnabled = !v
            binding.logTimestamp.isEnabled = !v
            lifecycleScope.launch(Dispatchers.IO) {
                Logs.disabled = v

            }
        }
        binding.logLevel.addTextChangedListener {
            lifecycleScope.launch(Dispatchers.IO) {
                Logs.level =
                    LogLevel.levelOf(it).level
            }
        }
        binding.logTimestamp.addTextChangedListener {
            lifecycleScope.launch(Dispatchers.IO) {
                Logs.timestamp =
                    YesNo.valueOf(this@LogSettingActivity, it).value()
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            reloadSettings()
        }

    }


    private suspend fun reloadSettings() {
        val logDisabled = Logs.disabled
        val logLevel = Logs.level
        val logTimestamp = Logs.timestamp
        withContext(Dispatchers.Main) {
            binding.logDisabled.text =
                YesNo.valueOf(!logDisabled)
                    .getString(this@LogSettingActivity)
            binding.logLevel.text = logLevel
            binding.logTimestamp.text =
                YesNo.valueOf(logTimestamp)
                    .getString(this@LogSettingActivity)
            binding.logDisabled.setSimpleItems(R.array.yes_no)
            binding.logLevel.setSimpleItems(R.array.log_level)
            binding.logTimestamp.setSimpleItems(R.array.yes_no)

        }
    }


}