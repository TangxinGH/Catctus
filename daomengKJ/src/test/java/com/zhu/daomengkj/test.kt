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

    fun callback(callback:(String) ->Unit){
        callback  .invoke("heloo ")
    }

    @Test
    fun cb() {
         callback { println(it); println("cb ing ") }
    }

    @Test
    fun regex2() {
        val str="{\"code\":\"100\",\"data\":{\"haveMore\":true,\"total\":12387,\"list\":[{\"aid\":\"2551750\",\"activityId\":\"2551750\",\"imageUrl\":\"https://image.5idream.net/1231604806346315?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"离退处引导老同志体检志愿活动\",\"status\":\"2\",\"statusText\":\"规划中\",\"activitytime\":\"2020.11.10 至 2020.11.10\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2546844\",\"activityId\":\"2546844\",\"imageUrl\":\"https://image.5idream.net/1231604713607594?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"测绘学院-校运会摄影比赛\",\"status\":\"2\",\"statusText\":\"规划中\",\"activitytime\":\"2020.11.09 至 2020.11.10\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2544310\",\"activityId\":\"2544310\",\"imageUrl\":\"https://image.5idream.net/7FFD5DF6-F553-4E46-B0C7-8873038493AF?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"理学院信科20-1团支部“放飞青春梦想，弘扬家国情怀”活动\",\"status\":\"2\",\"statusText\":\"规划中\",\"activitytime\":\"2020.11.10 至 2020.11.10\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2541532\",\"activityId\":\"2541532\",\"imageUrl\":\"https://image.5idream.net/36347286/1604637114438_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"行政管理20-2班团支部“青春为家国，逐梦新未来”团日活动\",\"status\":\"2\",\"statusText\":\"规划中\",\"activitytime\":\"2020.11.10 至 2020.11.10\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2537222\",\"activityId\":\"2537222\",\"imageUrl\":\"https://image.5idream.net/34262599/1604580134368_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"旅院-风景19-3团支部“筑梦青春，心系家国”主题团日活动\",\"status\":\"2\",\"statusText\":\"规划中\",\"activitytime\":\"2020.11.13 至 2020.11.13\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2532318\",\"activityId\":\"2532318\",\"imageUrl\":\"https://image.5idream.net/6DB4B177-E909-4CD1-83A8-13EF3C323FA2?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"材料学院-2020年暑期“三下乡”分享会（雁山）\",\"status\":\"2\",\"statusText\":\"规划中\",\"activitytime\":\"2020.11.10 至 2020.11.10\",\"catalog2name\":\"实践实习\"},{\"aid\":\"2523968\",\"activityId\":\"2523968\",\"imageUrl\":\"https://image.5idream.net/34262830/1604465691235_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"旅院—酒管19-2团支部＂筑梦青春，心系家国＂主题团日活动\",\"status\":\"2\",\"statusText\":\"规划中\",\"activitytime\":\"2020.11.13 至 2020.11.13\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2547922\",\"activityId\":\"2547922\",\"imageUrl\":\"https://image.5idream.net/1231604724694102?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"全媒体学生中心—校运会公益活动\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.09 至 2020.11.09\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2547638\",\"activityId\":\"2547638\",\"imageUrl\":\"https://image.5idream.net/34261936/1604723258492_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"桂工讲坛-空间科学实验技术及其应用\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.11 至 2020.11.11\",\"catalog2name\":\"创新创业\"},{\"aid\":\"2544139\",\"activityId\":\"2544139\",\"imageUrl\":\"https://image.5idream.net/1231604656478261?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"2020年10月妇幼医院志愿者公益时数发放\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.10 至 2020.11.10\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2543121\",\"activityId\":\"2543121\",\"imageUrl\":\"https://image.5idream.net/1231604646538085?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"材料学院-10月废品回收活动公益时数\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.11 至 2020.11.11\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2540435\",\"activityId\":\"2540435\",\"imageUrl\":\"https://image.5idream.net/34264749/1604629939577_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"校团委•学生部“学习党的十九届五中全会精神”团日活动\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.10 至 2020.11.10\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2536736\",\"activityId\":\"2536736\",\"imageUrl\":\"https://image.5idream.net/1231604576201522?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"化生学院-沪江网校专题讲座\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.10 至 2020.11.10\",\"catalog2name\":\"技能特长\"},{\"aid\":\"2534812\",\"activityId\":\"2534812\",\"imageUrl\":\"https://image.5idream.net/1231604563673537?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"地学院—2020年新生才艺大赛（参演人员）\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.11 至 2020.11.12\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2531113\",\"activityId\":\"2531113\",\"imageUrl\":\"https://image.5idream.net/1231604539510184?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"管媒学院-青年励志社清洁区打扫（第十周）\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.09 至 2020.11.09\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2530898\",\"activityId\":\"2530898\",\"imageUrl\":\"https://image.5idream.net/1231604579632789?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"管媒学院-社工答辩会志愿者\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.09 至 2020.11.09\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2525442\",\"activityId\":\"2525442\",\"imageUrl\":\"https://image.5idream.net/34264049/1604476120497_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"土建学院－土木实验19-1团支部与国庆庚子携手奏华章团日活动\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.15 至 2020.11.16\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2451531\",\"activityId\":\"2451531\",\"imageUrl\":\"https://image.5idream.net/34264225/1603666734440_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"外国语学院－20级校史校情教育\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.11 至 2020.11.12\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2451522\",\"activityId\":\"2451522\",\"imageUrl\":\"https://image.5idream.net/34264225/1603665974603_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"外国语学院-20级新生启航教育\",\"status\":\"3\",\"statusText\":\"报名中\",\"activitytime\":\"2020.11.11 至 2020.11.12\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2550932\",\"activityId\":\"2550932\",\"imageUrl\":\"https://image.5idream.net/8FF71B4E-3AA5-47F9-AF10-88D97E5DC4A9?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"【桂工讲坛1163讲】弘扬湘江战役精神，走好新时代长征路\",\"status\":\"4\",\"statusText\":\"等待中\",\"activitytime\":\"2020.11.09 至 2020.11.09\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2546684\",\"activityId\":\"2546684\",\"imageUrl\":\"https://image.5idream.net/1231604711148564?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"信息学院—2020校园模拟招聘大赛（观众）\",\"status\":\"4\",\"statusText\":\"等待中\",\"activitytime\":\"2020.11.09 至 2020.11.09\",\"catalog2name\":\"技能特长\"},{\"aid\":\"2543918\",\"activityId\":\"2543918\",\"imageUrl\":\"https://image.5idream.net/36286611/1604652249712_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"理学院统计20-1团支部“放飞青春梦想，弘扬家国情怀”活动\",\"status\":\"4\",\"statusText\":\"等待中\",\"activitytime\":\"2020.11.12 至 2020.11.12\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2538146\",\"activityId\":\"2538146\",\"imageUrl\":\"https://image.5idream.net/1231604586609744?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"材料学院-第四届简历大赛暨模拟面试比赛\",\"status\":\"4\",\"statusText\":\"等待中\",\"activitytime\":\"2020.11.09 至 2020.11.09\",\"catalog2name\":\"技能特长\"},{\"aid\":\"2527156\",\"activityId\":\"2527156\",\"imageUrl\":\"https://image.5idream.net/1231604486025808?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"自管委十月份卫生检查公益时数认证\",\"status\":\"4\",\"statusText\":\"等待中\",\"activitytime\":\"2020.11.14 至 2020.11.14\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2525957\",\"activityId\":\"2525957\",\"imageUrl\":\"https://image.5idream.net/36347875/1604478682594_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"艺术学院-桂工杯篮球赛（运动员）\",\"status\":\"4\",\"statusText\":\"等待中\",\"activitytime\":\"2020.11.09 至 2020.11.10\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2517149\",\"activityId\":\"2517149\",\"imageUrl\":\"https://image.5idream.net/1231604395728933?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"土建学院-新生杯辩论赛（辩手，评委）\",\"status\":\"4\",\"statusText\":\"等待中\",\"activitytime\":\"2020.11.09 至 2020.11.09\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2515628\",\"activityId\":\"2515628\",\"imageUrl\":\"https://image.5idream.net/34261833/1604386768876_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"机械与控制工程学院-公益课程培训\",\"status\":\"4\",\"statusText\":\"等待中\",\"activitytime\":\"2020.11.09 至 2020.11.09\",\"catalog2name\":\"实践实习\"},{\"aid\":\"2543917\",\"activityId\":\"2543917\",\"imageUrl\":\"https://image.5idream.net/4D35AA91-9EB8-4960-8EEC-255FBD7BDCFE?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"旅院—办公室资料整理活动\",\"status\":\"5\",\"statusText\":\"进行中\",\"activitytime\":\"2020.11.08 至 2020.11.08\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2539440\",\"activityId\":\"2539440\",\"imageUrl\":\"https://image.5idream.net/1231604622601351?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"商学院-2020级新生才艺大赛（观众）\",\"status\":\"5\",\"statusText\":\"进行中\",\"activitytime\":\"2020.11.08 至 2020.11.08\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2535425\",\"activityId\":\"2535425\",\"imageUrl\":\"https://image.5idream.net/1231604565694609?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"艺术学院-第49届校运会方块队\",\"status\":\"5\",\"statusText\":\"进行中\",\"activitytime\":\"2020.11.08 至 2020.11.09\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2510267\",\"activityId\":\"2510267\",\"imageUrl\":\"https://image.5idream.net/1231604325014890?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"土建学院-校运会方块队志愿公益活动\",\"status\":\"5\",\"statusText\":\"进行中\",\"activitytime\":\"2020.11.08 至 2020.11.09\",\"catalog2name\":\"志愿公益\"},{\"aid\":\"2452904\",\"activityId\":\"2452904\",\"imageUrl\":\"https://image.5idream.net/36343760/1603679158317_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"材料20-6团支部“以青春之名，担时代之责”主题团日活动\",\"status\":\"5\",\"statusText\":\"进行中\",\"activitytime\":\"2020.11.08 至 2020.11.08\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2549910\",\"activityId\":\"2549910\",\"imageUrl\":\"https://image.5idream.net/3020433/1604752970474_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"机械20-3班团支部“与祖国同行，为人民服务”团日活动\",\"status\":\"6\",\"statusText\":\"已结束\",\"activitytime\":\"2020.11.08 至 2020.11.08\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2547417\",\"activityId\":\"2547417\",\"imageUrl\":\"https://image.5idream.net/1231604720220181?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"信息学院-第二课堂成绩单暨到梦空间培训会（二）\",\"status\":\"6\",\"statusText\":\"已结束\",\"activitytime\":\"2020.11.08 至 2020.11.08\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2546604\",\"activityId\":\"2546604\",\"imageUrl\":\"https://image.5idream.net/34260696/1604709987548_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"桂林理工大学书画协会书法公开课\",\"status\":\"6\",\"statusText\":\"已结束\",\"activitytime\":\"2020.11.08 至 2020.11.08\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2544842\",\"activityId\":\"2544842\",\"imageUrl\":\"https://image.5idream.net/36284975/1604663076488_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"土木20实验班“致青春，为家国-争做黄文秀式好青年”团日活动\",\"status\":\"6\",\"statusText\":\"已结束\",\"activitytime\":\"2020.11.07 至 2020.11.07\",\"catalog2name\":\"思想成长\"},{\"aid\":\"2541777\",\"activityId\":\"2541777\",\"imageUrl\":\"https://image.5idream.net/36347103/1604638629606_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"旅院-绘旅才艺大赛决赛（观众）\",\"status\":\"6\",\"statusText\":\"已结束\",\"activitytime\":\"2020.11.07 至 2020.11.07\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2540431\",\"activityId\":\"2540431\",\"imageUrl\":\"https://image.5idream.net/34260022/1604630227238_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"桂林理工大学棋类协会公开课—围棋培训班\",\"status\":\"6\",\"statusText\":\"已结束\",\"activitytime\":\"2020.11.07 至 2020.11.07\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2538870\",\"activityId\":\"2538870\",\"imageUrl\":\"https://image.5idream.net/34261966/1604596392362_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"桂林理工大学第一届“新生杯”辩论赛半决赛第二场\",\"status\":\"6\",\"statusText\":\"已结束\",\"activitytime\":\"2020.11.08 至 2020.11.08\",\"catalog2name\":\"文体活动\"},{\"aid\":\"2538866\",\"activityId\":\"2538866\",\"imageUrl\":\"https://image.5idream.net/34261966/1604593819283_0?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg\",\"name\":\"桂林理工大学第一届“新生杯”辩论赛半决赛第一场\",\"status\":\"6\",\"statusText\":\"已结束\",\"activitytime\":\"2020.11.08 至 2020.11.08\",\"catalog2name\":\"文体活动\"}]}}"
//        Regex("\"catalog2name\":\"(.+?)\"").find(str)?.groupValues
//        Regex("\"catalog2name\":\"(.+?)\"").findAll(str).iterator().forEachRemaining {
//            println(it.groupValues[1])
//        }
//        Regex("思想成长|实践实习").findAll(str).iterator().forEachRemaining {
//            println(it.groupValues[0])
//        }
        Regex("思想成长|实践实习").findAll(str) .distinct().iterator().forEachRemaining {
            println(it.groupValues[0])
        }
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