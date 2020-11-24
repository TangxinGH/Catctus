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
 data class act_info (

	  val code : Int=0,
	  val data : DataInfo

) {
	override fun toString(): String {
		return "活动信息：$data)"
	}
}


@Serializable
 data class DataInfo (

	val activityId : Int=0,
	val activityName : String="",
	val activityImgSet : List<String>,
	val status : Int=0,
	val statusText : String="",
	val joinNum : Int=0,
	val address : String="",
	val addressLongitude : Double,
	val addressLatitude : Double,
	val tribeVo : TribeVo,
	val content : String="",
	val joinTip : String="",
	val joinWay : Int=0,
	val joinWayDesc : String="",
	val score : Float,
	val scoreFlag : Boolean=false,
	val pubSnapshotFlag : Boolean=false,
	val joinmaxnum : Int=0,
	val level : Int=0,
	val levelText : String="",
	val joinrange : Int=0,
	val joinrangeText : String="",
	val joindaterange : Int=0,
	val labelname : String="",
	val joindate : String="",
	val startdate : String="",
	val isLeave : Boolean=false,
	val countdownText : String="",
	val countdown : Int=0,
	val buttonType : Int=0,
	val manageFlag : Boolean=false,
	val ableJoinFlag : Int=0,
	val unableJoinReason : String="",
	val collectFlag : Boolean=false,
	val signinFlag : Boolean=false,
	val recordFlag : Boolean=false,
	val signUpId : Int=0,
	val isHitWay : Boolean=false,
	val isCheckWay : Boolean=false,
	val isJoinWay : Boolean=false,
	val isChecking : Boolean=false,
	val isBegin : Boolean=false,
	val isEnd : Boolean=false,
	val isCancel : Boolean=false,
	val canSetSiginCode : Boolean=false,
	val canJoin : Boolean=false,
	val isTribeActivity : Boolean=false,
	val isCollegeActivity : Boolean=false,
	val isSchoolActivity : Boolean=false,
	val joinCanCancel : Boolean=false,
	val inPlan : Boolean=false,
	val injoin : Boolean=false,
	val iswait : Boolean=false,
	val isrecord : Boolean=false,
	val isgroupjoin : Boolean=false,
	val endPassJoin : Boolean=false,
	val end : Boolean=false,
	val cancel : Boolean=false,
	val hitWay : Boolean=false,
	val checkWay : Boolean=false,
	val checking : Boolean=false,
	val begin : Boolean=false,
	val tribeActivity : Boolean=false,
	val collegeActivity : Boolean=false,
	val schoolActivity : Boolean=false
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
 data class TribeVo (
	val tribeId : Int=0,
	val imageUrl : String="",
	val name : String="",
	val typeDesc : String="",
	val member : Int=0,
	val tribeLevel : Int
) {
	override fun toString(): String {
		return "\n TribeVo( name='$name', typeDesc='$typeDesc')"
	}
}