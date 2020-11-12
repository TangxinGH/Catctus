import kotlinx.serialization.Serializable
import kotlin.collections.List

/* 

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */

@Serializable
data class actsJSON (
	  val code : String,
	  val data : act_Data
)
@Serializable
data class act_Data (

	   val haveMore : Boolean,
	  val total : Int,
	  val list : List<activities>
)


@Serializable
data class activities (

	  val aid : Int,
	  val activityId : Int,
	  val imageUrl : String,
	  val name : String,
	  val status : Int,
	  val statusText : String,
	  val activitytime : String,
	  val catalog2name : String
)