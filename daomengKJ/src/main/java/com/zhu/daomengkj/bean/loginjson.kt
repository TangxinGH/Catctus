package com.zhu.daomengkj.bean
import kotlinx.serialization.*
@Serializable
data class LoginJSON(val code: String,
                     val data: DataBean) {
    @Serializable
    data class DataBean(val uid: Int,
                        val token: String,
                        val name: String,
                        val nickname: String,
                        val collegeId: Int,
                        val collegeName: String,
                        val avatar: String,
                        val userType: String,
                        val displayName: String,
                        val schoolName: String,
                        val schoolId: String,
                        val schoolCustomOpen: Boolean,
                        val phone: String,
                        val email: String,
                        val studentId: String,
                        val joindate: String,
                        val activatFlag: String,
                        val myIdentity: String,
                        val reporter: Boolean,
                        val pubRecordAble: Boolean,
                        val studentNoRepeat: Boolean,
                        val recordMsg: String,
                        val account: String,
                        val myIdentityInfo: String,
                        val gender: String,
                        val professionalid: Int,
                        val pwdAvailable: Boolean,
                        val isRealname: Boolean,
                        val isGraduate: Boolean,
                        val nationwideColor: Int,
                        val userIdentity: String,
                        val messageTip: Boolean)
}