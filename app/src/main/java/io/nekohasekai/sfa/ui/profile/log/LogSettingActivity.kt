package io.nekohasekai.sfa.ui.profile.log

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import io.nekohasekai.sfa.R
import io.nekohasekai.sfa.constant.EnabledType
import io.nekohasekai.sfa.constant.LogLevel
import io.nekohasekai.sfa.constant.YesNo
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
        binding.logLevel.isEnabled = Logs.enabled
        binding.logEnabled.addTextChangedListener {
            val v = EnabledType.valueOf(this@LogSettingActivity, it).boolValue
            binding.logLevel.isEnabled = v
            lifecycleScope.launch(Dispatchers.IO) {
                Logs.enabled = v

            }
        }
        binding.logLevel.addTextChangedListener {
            lifecycleScope.launch(Dispatchers.IO) {
                Logs.level =
                    LogLevel.levelOf(it).level
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            reloadSettings()
        }

    }


    private suspend fun reloadSettings() {
        val logEnabled = Logs.enabled
        val logLevel = Logs.level
        withContext(Dispatchers.Main) {
            binding.logEnabled.text =
                EnabledType.from(logEnabled)
                    .getString(this@LogSettingActivity)
            binding.logLevel.text = logLevel
            binding.logEnabled.setSimpleItems(R.array.enabled)
            binding.logLevel.setSimpleItems(R.array.log_level)

        }
    }


}