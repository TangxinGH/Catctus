/* 
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
 data class act_info (

	  val code : Int,
	  val data : DataInfo

) {
	override fun toString(): String {
		return "活动信息：$data)"
	}
}


@Serializable
 data class DataInfo (

	val activityId : Int,
	val activityName : String,
	val activityImgSet : List<String>,
	val status : Int,
	val statusText : String,
	val joinNum : Int,
	val address : String,
	val addressLongitude : Double,
	val addressLatitude : Double,
	val tribeVo : TribeVo,
	val content : String,
	val joinTip : String,
	val joinWay : Int,
	val joinWayDesc : String,
	val score : Float,
	val scoreFlag : Boolean,
	val pubSnapshotFlag : Boolean,
	val joinmaxnum : Int,
	val level : Int,
	val levelText : String,
	val joinrange : Int,
	val joinrangeText : String,
	val joindaterange : Int,
	val labelname : String,
	val joindate : String,
	val startdate : String,
	val isLeave : Boolean,
	val countdownText : String,
	val countdown : Int,
	val buttonType : Int,
	val manageFlag : Boolean,
	val ableJoinFlag : Int,
	val unableJoinReason : String,
	val collectFlag : Boolean,
	val signinFlag : Boolean,
	val recordFlag : Boolean,
	val signUpId : Int,
	val isHitWay : Boolean,
	val isCheckWay : Boolean,
	val isJoinWay : Boolean,
	val isChecking : Boolean,
	val isBegin : Boolean,
	val isEnd : Boolean,
	val isCancel : Boolean,
	val canSetSiginCode : Boolean,
	val canJoin : Boolean,
	val isTribeActivity : Boolean,
	val isCollegeActivity : Boolean,
	val isSchoolActivity : Boolean,
	val joinCanCancel : Boolean,
	val inPlan : Boolean,
	val injoin : Boolean,
	val iswait : Boolean,
	val isrecord : Boolean,
	val isgroupjoin : Boolean,
	val endPassJoin : Boolean,
	val end : Boolean,
	val cancel : Boolean,
	val hitWay : Boolean,
	val checkWay : Boolean,
	val checking : Boolean,
	val begin : Boolean,
	val tribeActivity : Boolean,
	val collegeActivity : Boolean,
	val schoolActivity : Boolean
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
	val tribeId : Int,
	val imageUrl : String,
	val name : String,
	val typeDesc : String,
	val member : Int,
	val tribeLevel : Int
) {
	override fun toString(): String {
		return "\n TribeVo( name='$name', typeDesc='$typeDesc')"
	}
}