package com.zhu.daomengkj

import com.zhu.daomengkj.jiami.DES_ECB_Encrypt
import com.zhu.daomengkj.Interceptor.AddHeadersInterceptor
import com.zhu.daomengkj.Interceptor.LoggingInterceptor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

class UnitTest {



      suspend fun doSomethingUsefulOne(): Int {
        delay(3000L) // 假设我们在这里做了一些有用的事
          println("ddfa 夺")
        return 13
    }

    @Test
    fun time() {
       val time= SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.SIMPLIFIED_CHINESE).parse("2020.11.13 16:30").time
        println(Date(time))
        println(System.currentTimeMillis()-time)
        println((System.currentTimeMillis()-time)%1000)
    }

    @Test
    fun regex() {
        val str="{\"code\":\"100\",\"data\":{\"activityId\":2570399,\"activityName\":\"移动通讯的千里眼和顺风耳—微波介质陶瓷(屏风)\",\"activityImgSet\":[\"https://image.5idream.net/2B9E766F-6704-41DA-BAD4-EBABAA720DE0?x-oss-process=image/resize,w_1334,h_0/quality,Q_100/format,jpg\"],\"status\":\"3\",\"statusText\":\"报名中\",\"joinNum\":171,\"address\":\"屏风校区图书馆报告厅\",\"addressLongitude\":\"110.324540\",\"addressLatitude\":\"25.287551\",\"tribeVo\":{\"tribeId\":\"146292\",\"imageUrl\":\"https://image.5idream.net/123/1537525340413?x-oss-process=image/crop,x_0,y_0,w_0,h_0/quality,q_80\",\"name\":\"人文素质教育教学部\",\"typeDesc\":\"校级，校院团委，人文素质教育教学部\",\"member\":\"9\",\"tribeLevel\":\"1\"},\"content\":\"周焕福，男，汉族，1979年生，贵州镇宁人，桂林理工大学发监中心副主任、学科办副主任，博士，教授，博士研究生导师，主要从事微波介质材料与器件以及压敏材料与器件的研究工作。\\n一、教育与工作经历\\n2009.9-至今，桂林理工大学材料科学与工程学院，材料科学与工程学院，讲师、副教授、教授。\\n2012.1-2013.1，新加坡南洋理工大学材料科学与工程学院，访问学者。\\n2010.10-2014.10，广西北海新未来信息产业股份有限公司，企业科技特派员。\\n2006.9－2009.7，西安交通大学，电子陶瓷与器件教育部重点实验室，电子科学与技术专业，博士研究生。\\n二、教学\\n讲授本科生课程：材料无损检测，金属功能材料。\\n讲授研究生课程：材料物理化学，信息功能材料。\\n三、科学研究工作\\n主要从事新型微波介质材料与器件、锂离子导体材料以及压敏材料与器件的相关研究。主持国家自然科学基金3项，广西自然科学基金4项，广西科学研究与技术开发计划项目1项，企业委托开发重大项目1项。作为技术负责人参与广西科学研究与技术开发计划项目2项。作为技术骨干参与完成了国家863课题1项，国家973项目1项。在“Journal of the American Ceramic Society”、“Journal of Materials Research”、“Journal of Electronic Materials”等国际材料学知名学术期刊上发表SCI论文90余篇，获得授权中国发明专利10余项，美国发明专利1项，多次在国际学术会议上进行了交流，现为““Dalton Transactions”、“Journal of Materials Chemistry C”、“Materials Science and Engineering B”等国际期刊的审稿人。入选2017年广西自然科学基金杰出青年基金获得者、2014年广西高等学校优秀中青年骨干教师培养工程培养对象；获得2013年获得广西自然科学二等奖1项，2015年北海市科技进步二等奖，2011、2012年获得国际衍射中心重要贡献奖各1项，2012年获得广西自然科学优秀论文3等奖1项，2010年被广西壮族自治区科技厅聘为第三批科技特派员（驻广西新未来科技股份有限公司）。\\n近年来在研与完成的项目：\\n1. 国家自然科学基金地区基金项目，61761015，LTCC滤波器用MgO-B2O3二元体系低介、高Q微波介电材料的成分设计、缺陷调控与性能研究，2018/01-2021/12，37万元，主持\\n2. 国家自然科学基金地区基金项目，11464009，B位复合尖晶石陶瓷的有序/无序相变、畴结构、缺陷与微波介电性能的研究，2015/01-2018/12，60万元，主持\\n3. 国家自然科学基金青年基金项目，51102058，Li2O-MO-TiO2三元尖晶石化合物的微波介电与离子导电特性研究，2012/01-2014/12，25万元，主持\\n4. 广西自然科学基金杰出青年项目，2017GXNSFFA198011，新型有色金属氧化物微波介电陶瓷及器件应用基础研究，2017/09-2021/09，60万元，主持\\n5. 广西自然科学基金重点项目，2015GXNSFDA139033，钒基石榴石低温共烧LTCC陶瓷的成分设计、缺陷调控与微波介电性能研究，2015/09-2018/08，30万元，主持\\n6. 深圳顺络电子股份有限公司委托开发项目，449-LTP1412，陶瓷粉合作开发，2015/03-2016/03，60万元，主持\\n7. 广西科学研究与技术开发计划，桂科攻1348020-11，叠层片式化电冲击浪涌抑制器产业化过程中的关键技术研究，2013/01-2014/12，40万元，主持\\n8. 广西自然科学基金面上项目，2014GXNSFAA118312，A4MTi11O27型LTCC陶瓷的成分设计、结构调控与微波介电性能研究，2014/06-2017/05，5万元，主持\\n9. 广西自然科学基金青年基金项目，2011GXNSFB018012，Li2B1B23O8型锂基尖晶石化合物的制备、介电与导电性能调控及其机理研究，2011/03-2013/05，5万元，主持\\n10. 广西有色金属隐伏矿床勘查及材料开发协同创新中心团队项目，有色金属信息功能材料与片式化器件GXYSXTZX2016-Ⅱ-1，2016.01-2018.12，55万元，团队负责人。\\n \\n科研获奖： \\n1 广西自然科学基金杰出青年基金获得者，广西科技厅，省部级，人才奖励，2017。\\n2 广西高等学校优秀中青年骨干教师，广西教育厅，地厅级，人才奖励，2014。\\n3 类钙钛矿有色金属复合氧化物的合成、结构与介电性能，广西区人民政府，2013广西自然科学奖，二等奖，2014。\\n4 新型电子元器件低成本化制备过程中的关键技术研究，北海市人民政府，2015年北海市科技进步二等奖，2015。\\n5 2011、2012年国际衍射中心重要贡献奖，美国国际衍射中心，协会奖励，一级，2011、2012。\\n6新型Ba4ZnTi11O27微波介质陶瓷的制备与性能研究，广西自然科学优秀论文奖评审工作领导小组，协会奖励，三等奖，2012。\\n7周焕福1/1，桂林理工大学十佳青年科技工作者，桂林理工大学，桂林理工大学校级奖励，2012。\",\"joinTip\":\"入场时需纸质签到，退场时需二维码签退，两次签到方可获得学分。本次活动不予请假，请报名的同学提前十五分钟入场。活动时需遵守纪律，结束时有关签到问题必须及时联系在场负责人员进行补签。\",\"joinWay\":\"2\",\"joinWayDesc\":\"报名制：\\n报名先到先得，报名人数达到预设的最大参与人数，则无法继续报名。报名成功者即可参加活动。\",\"score\":0.0,\"scoreFlag\":false,\"pubSnapshotFlag\":false,\"joinmaxnum\":\"260\",\"level\":\"1\",\"levelText\":\"校级\",\"joinrange\":\"4\",\"joinrangeText\":\"不限\",\"labelname\":\"主题讲座\",\"joindate\":\"2020.11.10 16:30-2020.11.10 19:30\",\"startdate\":\"2020.11.11 14:30-2020.11.11 16:15\",\"specialList\":[{\"achievementid\":\"5074\",\"activityachievementid\":\"3643310\",\"name\":\"思想成长\",\"unitcount\":\"0.25\",\"maxprovidecount\":\"260\",\"providecount\":\"0\",\"accountTypeId\":\"624\",\"accountTypeName\":\"学分\"}],\"isLeave\":false,\"countdownText\":\"报名结束倒计时\",\"countdown\":9201,\"buttonType\":1,\"manageFlag\":false,\"ableJoinFlag\":1,\"collectFlag\":false,\"signinFlag\":false,\"recordFlag\":false,\"signUpId\":151015120,\"isHitWay\":false,\"isCheckWay\":false,\"isJoinWay\":false,\"isChecking\":false,\"isBegin\":false,\"isEnd\":false,\"isCancel\":false,\"canSetSiginCode\":false,\"canJoin\":false,\"isTribeActivity\":false,\"isCollegeActivity\":false,\"isSchoolActivity\":false,\"joinCanCancel\":false,\"inPlan\":false,\"injoin\":false,\"iswait\":false,\"isrecord\":false,\"isgroupjoin\":false,\"endPassJoin\":false,\"end\":false,\"cancel\":false,\"hitWay\":false,\"checkWay\":false,\"checking\":false,\"begin\":false,\"tribeActivity\":false,\"collegeActivity\":false,\"schoolActivity\":false}}\n"
        val regex = Regex("joindate\":\"(.+?)\"")
     val result=   regex.find(str)?.groupValues

//        println(result?.replace("\"","")?.split(":")?.get(1))
println()
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // 假设我们在这里也做了一些有用的事
          println( "daf dasfdsffdfhjytrtrr")
        return 29
    }
    @Test
    fun main() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val one =GlobalScope.async {      doSomethingUsefulOne() }
            val two =GlobalScope. async { doSomethingUsefulTwo() }

            println("The answer is ${one.await() }${ two.await()}")

        }
        println("Completed in $time ms")
    }
    @Test
    fun asny() {
        main()
       Thread.sleep(1000*5)
            println("春树暮云 ")

    }

    @Test
    fun inttime(){
      println(
          (100 * System.currentTimeMillis()).toInt()
      ) }
    @Test
    fun testokhttp() {
      val server=  MockWebServer()
          server.enqueue(MockResponse().setBody("code:100"))



        val url = "http://localhost.:8888/"
        val sss =  "account=" +"acc"+ "&pwd=" + DES_ECB_Encrypt("pwd") + "&version=4.3.6"
        //进行缓存
        val httpClientBuilder = OkHttpClient.Builder() //1.创建OkHttpClient对象
        val okHttpClient = httpClientBuilder
            .addInterceptor(AddHeadersInterceptor())
            .addInterceptor(LoggingInterceptor())//增加拦截器
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS). build() //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
//        val time=(100 * System.currentTimeMillis()).toInt()

        val call=  okHttpClient.newCall(Request.Builder().url(url)
            .post(FormBody.Builder().add("account","acc").add("pwd","xxx").add("version","version").build())
            .build()) // //3.创建一个call对象,参数就是Request请求对象
//4.请求加入调度，重写回调方法

        //4.请求加入调度，重写回调方法
        call.enqueue( object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                //请求失败执行的方法
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                //请求成功执行的方法
                val body= response.body?.string()
//                val res= JSONObject(body)
//                if (res["code"] == "100")

                    println(body)

            }
        })
        server.takeRequest()

    }
}