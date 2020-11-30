/* 
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
import kotlin.collections.List
import kotlinx.serialization.Serializable

/*主部设默认值 变成 可选值 有些没有 */
@Serializable
data class act_info(

    val code: Int = 0,
    val data: DataInfo

) {
    override fun toString(): String {
        return "活动信息：$data)"
    }

    fun getArray(): List<Pair<String, String>> {
        val hashMap = HashMap<String, String>()
        hashMap["code"] = code.toString()
        hashMap["activityId"] = data.activityId.toString()
        hashMap["activityId"] = data.activityId.toString()
        hashMap["activityName"] = data.activityName
        hashMap["status"] = data.status.toString()
        hashMap["statusText"] = data.statusText
        hashMap["joinNum"] = data.joinNum.toString()
        hashMap["address"] = data.address
        hashMap["addressLongitude"] = data.addressLongitude.toString()
        hashMap["addressLatitude"] = data.addressLatitude.toString()
        hashMap["tribeVo"] = data.tribeVo.toString()
        hashMap["content"] = data.content
        hashMap["joinTip"] = data.joinTip
        hashMap["joinWay"] = data.joinWay.toString()
        hashMap["joinWayDesc"] = data.joinWayDesc
        hashMap["score"] = data.score.toString()
        hashMap["scoreFlag"] = data.scoreFlag.toString()
        hashMap["pubSnapshotFlag"] = data.pubSnapshotFlag.toString()
        hashMap["joinmaxnum"] = data.joinmaxnum.toString()
        hashMap["level"] = data.level.toString()
        hashMap["levelText"] = data.levelText
        hashMap["joinrange"] = data.joinrange.toString()
        hashMap["joinrangeText"] = data.joinrangeText
        hashMap["joindaterange"] = data.joindaterange.toString()
        hashMap["labelname"] = data.labelname
        hashMap["joindate"] = data.joindate
        hashMap["startdate"] = data.startdate
        hashMap["isLeave"] = data.isLeave.toString()
        hashMap["countdownText"] = data.countdownText
        hashMap["countdown"] = data.countdown.toString()
        hashMap["buttonType"] = data.buttonType.toString()
        hashMap["manageFlag"] = data.manageFlag.toString()
        hashMap["ableJoinFlag"] = data.ableJoinFlag.toString()
        hashMap["unableJoinReason"] = data.unableJoinReason
        hashMap["collectFlag"] = data.collectFlag.toString()
        hashMap["signinFlag"] = data.signinFlag.toString()
        hashMap["recordFlag"] = data.recordFlag.toString()
        hashMap["signUpId"] = data.signUpId.toString()
        hashMap["isHitWay"] = data.isHitWay.toString()
        hashMap["isCheckWay"] = data.isCheckWay.toString()
        hashMap["isJoinWay"] = data.isJoinWay.toString()
        hashMap["isChecking"] = data.isChecking.toString()
        hashMap["isBegin"] = data.isBegin.toString()
        hashMap["isEnd"] = data.isEnd.toString()
        hashMap["isCancel"] = data.isCancel.toString()
        hashMap["canSetSiginCode"] = data.canSetSiginCode.toString()
        hashMap["canJoin"] = data.canJoin.toString()
        hashMap["isTribeActivity"] = data.isTribeActivity.toString()
        hashMap["isCollegeActivity"] = data.isCollegeActivity.toString()
        hashMap["isSchoolActivity"] = data.isSchoolActivity.toString()
        hashMap["joinCanCancel"] = data.joinCanCancel.toString()
        hashMap["inPlan"] = data.inPlan.toString()
        hashMap["injoin"] = data.injoin.toString()
        hashMap["iswait"] = data.iswait.toString()
        hashMap["isrecord"] = data.isrecord.toString()
        hashMap["isgroupjoin"] = data.isgroupjoin.toString()
        hashMap["endPassJoin"] = data.endPassJoin.toString()
        hashMap["end"] = data.end.toString()
        hashMap["cancel"] = data.cancel.toString()
        hashMap["hitWay"] = data.hitWay.toString()
        hashMap["checkWay"] = data.checkWay.toString()
        hashMap["checking"] = data.checking.toString()
        hashMap["begin"] = data.begin.toString()
        hashMap["tribeActivity"] = data.tribeActivity.toString()
        hashMap["collegeActivity"] = data.collegeActivity.toString()
        hashMap["schoolActivity"] = data.schoolActivity.toString()

        return hashMap.toList()
    }
}


@Serializable
data class DataInfo(

    val activityId: Int = 0,
    val activityName: String = "",
    val activityImgSet: List<String>? = null,
    val status: Int = 0,
    val statusText: String = "",
    val joinNum: Int = 0,
    val address: String = "",
    val addressLongitude: Double = 0.toDouble(),
    val addressLatitude: Double = 0.toDouble(),
    val tribeVo: TribeVo? = null,
    val content: String = "",
    val joinTip: String = "",
    val joinWay: Int = 0,
    val joinWayDesc: String = "",
    val score: Float = 0f,
    val scoreFlag: Boolean = false,
    val pubSnapshotFlag: Boolean = false,
    val joinmaxnum: String = "",
    val level: Int = 0,
    val levelText: String = "",
    val joinrange: String = "",
    val joinrangeText: String = "",
    val joindaterange: String = "",//int 疸
    val labelname: String = "",
    val joindate: String = "",
    val startdate: String = "",
    val isLeave: Boolean = false,
    val countdownText: String = "",
    val countdown: Int = 0,
    val buttonType: Int = 0,
    val manageFlag: Boolean = false,
    val ableJoinFlag: Int = 0,
    val unableJoinReason: String = "",
    val collectFlag: Boolean = false,
    val signinFlag: Boolean = false,
    val recordFlag: Boolean = false,
    val signUpId: Int = 0,
    val isHitWay: Boolean = false,
    val isCheckWay: Boolean = false,
    val isJoinWay: Boolean = false,
    val isChecking: Boolean = false,
    val isBegin: Boolean = false,
    val isEnd: Boolean = false,
    val isCancel: Boolean = false,
    val canSetSiginCode: Boolean = false,
    val canJoin: Boolean = false,
    val isTribeActivity: Boolean = false,
    val isCollegeActivity: Boolean = false,
    val isSchoolActivity: Boolean = false,
    val joinCanCancel: Boolean = false,
    val inPlan: Boolean = false,
    val injoin: Boolean = false,
    val iswait: Boolean = false,
    val isrecord: Boolean = false,
    val isgroupjoin: Boolean = false,
    val endPassJoin: Boolean = false,
    val end: Boolean = false,
    val cancel: Boolean = false,
    val hitWay: Boolean = false,
    val checkWay: Boolean = false,
    val checking: Boolean = false,
    val begin: Boolean = false,
    val tribeActivity: Boolean = false,
    val collegeActivity: Boolean = false,
    val schoolActivity: Boolean = false
) {
    override fun toString(): String {
        return "\nDataInfo(activityId=$activityId, \n" +
                "activityName='$activityName',\n" +
                "statusText='$statusText',\n" +
                " address='$address',\n" +
                "tribeVo=$tribeVo, \n " +
                "content='$content', \n" +
                "joinTip='$joinTip', \n " +
                "joinWayDesc='$joinWayDesc', \n " +
                "score=$score, \n " +
                "joinmaxnum=$joinmaxnum,   \n " +
                " levelText='$levelText',  joinrangeText='$joinrangeText', labelname='$labelname',\n " +
                " joindate='$joindate', startdate='$startdate',countdownText='$countdownText'"
    }
}


@Serializable
data class TribeVo(
    val tribeId: Int = 0,
    val imageUrl: String = "",
    val name: String = "",
    val typeDesc: String = "",
    val member: Int = 0,
    val tribeLevel: Int
) {
    override fun toString(): String {
        return "\n TribeVo( name='$name', typeDesc='$typeDesc')"
    }
}