package com.zhu.nav

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testact_detail() {
        val res="{\"code\":\"100\",\"data\":{\"scoreFlag\":false,\"isLeave\":false,\"manageFlag\":false,\"collectFlag\":false,\"signinFlag\":false,\"recordFlag\":false,\"isHitWay\":false,\"isCheckWay\":false,\"isJoinWay\":false,\"isChecking\":false,\"isBegin\":false,\"isEnd\":false,\"isCancel\":false,\"canSetSiginCode\":false,\"canJoin\":false,\"isTribeActivity\":false,\"isCollegeActivity\":false,\"isSchoolActivity\":false,\"joinCanCancel\":false,\"inPlan\":false,\"injoin\":false,\"iswait\":false,\"isrecord\":false,\"isgroupjoin\":false,\"endPassJoin\":false,\"end\":false,\"cancel\":false,\"hitWay\":false,\"checkWay\":false,\"checking\":false,\"begin\":false,\"tribeActivity\":false,\"collegeActivity\":false,\"schoolActivity\":false}}"
      println(  Json{isLenient=true ; ignoreUnknownKeys=true;}.decodeFromString(act_info.serializer(),res))



    }

    @Test
    fun testint() {
        val res="{\"code\":\"100\",\"data\":{\"activityId\":2785541,\"activityName\":\"自管委十一月份卫生检查公益时数认证（屏风）\",\"activityImgSet\":[\"https://image.5idream.net/1231606727818842?x-oss-process=image/resize,w_1334,h_0/quality,Q_100/format,jpg\"],\"status\":\"2\",\"statusText\":\"规划中\",\"joinNum\":0,\"address\":\"桂林理工大学屏风校区\",\"addressLongitude\":\"110.321987\",\"addressLatitude\":\"25.287280\",\"tribeVo\":{\"tribeId\":\"103710\",\"imageUrl\":\"https://image.5idream.net/1261297/1555923011085_0\",\"name\":\"桂林理工大学大学生宿舍自我管理委员会\",\"typeDesc\":\"校级，其他，其他\",\"member\":\"134\",\"tribeLevel\":\"1\"},\"content\":\"大学生宿舍自我管理委员会将要进行十一月份宿舍卫生检查，本次公益活动旨在为学生营造干净卫生的学习生活环境，希望更多的同学能参与到我们的公益活动中来。\\n\",\"joinTip\":\"仅限参加本次检查的志愿者报名\",\"joinWay\":\"3\",\"joinWayDesc\":\"评审制：\\n报名不分先后，人数可超过预设的最大参与人数，录取时间为报名开始到报名结束24小时前，若报名结束距活动开始时间小于24小时在活动开始前进行录取。报名成功且被录取者方可参与活动。\",\"score\":0.0,\"scoreFlag\":false,\"pubSnapshotFlag\":false,\"joinmaxnum\":\"不限\",\"level\":\"1\",\"levelText\":\"校级\",\"joinrange\":\"4\",\"joinrangeText\":\"不限\",\"labelname\":\"志愿公益\",\"joindate\":\"2020.12.01 14:00-2020.12.03 14:00\",\"startdate\":\"2020.12.04 10:00-2020.12.04 22:00\",\"specialList\":[{\"achievementid\":\"5071\",\"activityachievementid\":\"3987196\",\"name\":\"志愿公益\",\"unitcount\":\"3.50\",\"maxprovidecount\":\"16\",\"providecount\":\"0\",\"accountTypeId\":\"113\",\"accountTypeName\":\"学时\"},{\"achievementid\":\"5071\",\"activityachievementid\":\"3987197\",\"name\":\"志愿公益\",\"unitcount\":\"2.50\",\"maxprovidecount\":\"64\",\"providecount\":\"0\",\"accountTypeId\":\"113\",\"accountTypeName\":\"学时\"}],\"isLeave\":true,\"countdownText\":\"报名开始倒计时\",\"countdown\":63818,\"buttonType\":2,\"manageFlag\":false,\"ableJoinFlag\":1,\"collectFlag\":false,\"signinFlag\":false,\"recordFlag\":false,\"signUpId\":0,\"isHitWay\":false,\"isCheckWay\":false,\"isJoinWay\":false,\"isChecking\":false,\"isBegin\":false,\"isEnd\":false,\"isCancel\":false,\"canSetSiginCode\":false,\"canJoin\":false,\"isTribeActivity\":false,\"isCollegeActivity\":false,\"isSchoolActivity\":false,\"joinCanCancel\":false,\"inPlan\":false,\"injoin\":false,\"iswait\":false,\"isrecord\":false,\"isgroupjoin\":false,\"endPassJoin\":false,\"end\":false,\"cancel\":false,\"hitWay\":false,\"checkWay\":false,\"checking\":false,\"begin\":false,\"tribeActivity\":false,\"collegeActivity\":false,\"schoolActivity\":false}}"
        println(  Json{isLenient=true ; ignoreUnknownKeys=true;}.decodeFromString(act_info.serializer(),res))
    }
}