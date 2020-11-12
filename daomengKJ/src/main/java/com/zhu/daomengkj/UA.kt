package com.zhu.daomengkj

/**
 * @author jsonff.com
 */
class UA private constructor(b: Builder) {
    var channelName: String?
    var countryCode: String?
    var createTime: Int
    var device: String?
    var hardware: String?
    var modifyTime: Int
    var operator: String?
    var screenResolution: String?
    var startTime: Int
    var sysVersion: String?
    var system: String?
    var uuid: String?
    var version: String?

    class Builder {
        var channelName: String? = "dmkj_Android"
        var countryCode: String? = "US"
        var createTime = 0
        var device: String? = "xiaomi Redmi Note 7 Pro"
        var hardware: String? = "qcom"
        var modifyTime = 0
        var operator: String? = "%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8"
        var screenResolution: String? = "1080-2131"
        var startTime = 0
        var sysVersion: String? = "Android 29 10"
        var system: String? = "android"
        var uuid: String? = "7C:03:AB:21:F1:DD"
        var version: String? = "4.3.6"
        fun channelName(channelName: String?): Builder {
            this.channelName = channelName
            return this
        }

        fun countryCode(countryCode: String?): Builder {
            this.countryCode = countryCode
            return this
        }

        fun createTime(createTime: Int): Builder {
            this.createTime = createTime
            return this
        }

        fun device(device: String?): Builder {
            this.device = device
            return this
        }

        fun hardware(hardware: String?): Builder {
            this.hardware = hardware
            return this
        }

        fun modifyTime(modifyTime: Int): Builder {
            this.modifyTime = modifyTime
            return this
        }

        fun operator(operator: String?): Builder {
            this.operator = operator
            return this
        }

        fun screenResolution(screenResolution: String?): Builder {
            this.screenResolution = screenResolution
            return this
        }

        fun startTime(startTime: Int): Builder {
            this.startTime = startTime
            return this
        }

        fun sysVersion(sysVersion: String?): Builder {
            this.sysVersion = sysVersion
            return this
        }

        fun system(system: String?): Builder {
            this.system = system
            return this
        }

        fun uuid(uuid: String?): Builder {
            this.uuid = uuid
            return this
        }

        fun version(version: String?): Builder {
            this.version = version
            return this
        }

        fun build(): UA {
            return UA(this)
        }

        companion object {
            val instance: Builder
                get() = Builder()
        }
    }

    init {
        channelName = b.channelName
        countryCode = b.countryCode
        createTime = b.createTime
        device = b.device
        hardware = b.hardware
        modifyTime = b.modifyTime
        operator = b.operator
        screenResolution = b.screenResolution
        startTime = b.startTime
        sysVersion = b.sysVersion
        system = b.system
        uuid = b.uuid
        version = b.version
    }
}