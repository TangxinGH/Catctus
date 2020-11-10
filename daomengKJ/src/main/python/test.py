from datetime import datetime
from urllib.parse import urlencode
import requests
import time
import jsonpath
from jiami import *
from java import jclass


class Post:
    def __init__(self):
        java__static = jclass("com.zhu.daomengkj.Py_invoke_Java")
        self.instance_father=java__static.INSTANCE

    instance_father = None #则父类的属性不会被初始化，因而此时调用子类中name属性不存在



    standardUA = {"channelName": "dmkj_Android", "countryCode": "US", "createTime": int(100 * time.time()),
                  "device": "xiaomi Redmi Note 7 Pro", "hardware": "qcom", "modifyTime": int(100 * time.time()),
                  "operator": "%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8", "screenResolution": "1080-2131",
                  "startTime": int(100 * time.time()) + 19606523, "sysVersion": "Android 29 10", "system": "android",
                  "uuid": "7C:03:AB:21:F1:DD", "version": "4.3.6"}
    headers = {
        'standardUA': str(standardUA),
        'Content-Type': 'application/x-www-form-urlencoded',
        'Host': 'appdmkj.5idream.net',
        'Connection': 'Keep-Alive',
        'Accept-Encoding': 'gzip',
        'User-Agent': 'okhttp/3.11.0'
    }


    def get_ids(self, token, uid):
        sort_by_default={
            "level":"",
            "sort":"",
            "version":"4.3.6",
            "token":token,
            "joinStartTime":"",
            "catalogId2":"",
            "uid":uid,
            "catalogId":"",
            "collegeFlag":"",
            "joinEndTime":"",
            "startTime":"",
            "joinFlag":"",
            "endTime":"",
            "page":"1",
            "keyword":"",
            "specialFlag":"",
            "status":""
        }


        url = "https://appdmkj.5idream.net/v2/activity/activities"

        res = requests.post(url, headers=self.headers, data=urlencode(sort_by_default)).json()
        # print(res)
        names = jsonpath.jsonpath(res, '$..name')
        ids = jsonpath.jsonpath(res, '$..aid')
        statusTexts = jsonpath.jsonpath(res, '$..statusText')
        self.names = names
        self.ids = ids
        self.statusTexts = statusTexts
        return res


    def get_info(self, id, token, uid):
        str1={
            "uid":uid,
            "activityId":id,
            "version":"4.3.6",
            "token":token
        }
        print("进入get_info")
        url = "https://appdmkj.5idream.net/v2/activity/detail"
        res = requests.post(url, headers=self.headers, data=urlencode(str1))
        # signUpId = str(res.json()['data']['signUpId'])
        try:
            print("查询detail，res",res.json())
            print("查询到的活动detail的时间：",res.json()['data']['joindate'])
            return res.json()
        except:
            return False

    def get_can_join(self, token, uid): # 查看仅可以报名的活动
        sort_by = {"level": "", "sort": "", "version": "4.3.6", "token": token,
                   "joinStartTime": "",
                   "catalogId2": "", "uid": uid,
                   "catalogId": "", "collegeFlag": "", "joinEndTime": "", "startTime": "",
                   "joinFlag": "1",
                   "endTime": "", "page": "1", "keyword": "", "specialFlag": "", "status": ""}
        url = "https://appdmkj.5idream.net/v2/activity/activities"

        res = requests.post(url, headers=self.headers, data=urlencode(sort_by)).json()
        print("能加入仅我的活动:",res)
        if res['data']['list']:
            names = jsonpath.jsonpath(res, '$..name')
            ids = jsonpath.jsonpath(res, '$..aid')
            statusTexts = jsonpath.jsonpath(res, '$..statusText')
            self.names = names
            self.ids = ids
            self.statusTexts = statusTexts
            return True
        else:
            pass

    def join(self, id, token, uid,act_start_time):
        print("打印instance_father",self.instance_father)
        self.instance_father.showinfo("报名instance","进入流程")
        str2={
            "uid":uid,
            "activityId":id,
            "data":"[]",
            "remark":"",
            "version":"4.3.6",
            "token":token
        }

        url = 'https://appdmkj.5idream.net/v2/signup/submit'

        # 应该这里是报名
        print("开始报名")
        print("报名时间为：" , act_start_time)
        if not act_start_time:
            self.instance_father.showwarning("出错了","报名时间出问题")
            return False
        # 对时间进行转换
        start_time = datetime.strptime(act_start_time, '%Y.%m.%d %H:%M')
        # '2020.11.07 15:00-2020.11.07 18:00'
        if start_time < datetime.now():
            print("过时了或者正在报名，")
            self.instance_father.showinfo("正在报名",str(id))
            res = requests.post(url, headers=self.headers, data=urlencode(str2))
            try:
                self.instance_father.showinfo("报名结果",res.json())
                return res.json()
            except:
                return False

        # start_time = datetime(2020, 11, 5, 19, 49)  # 用 指定日期时间创建datetime
        now_time = datetime.now()  # 现在的时间
        sleep_time = start_time - now_time  # 时间差
        self.instance_father.showinfo('活动未开始，将睡眠秒', str(sleep_time.seconds + 1.08))
        time.sleep(sleep_time.seconds + 1.08)  # 单位为秒 ,延迟 80毫秒 阻塞  . 加1秒

        res = requests.post(url, headers=self.headers, data=urlencode(str2))
        try:
            return res.json()
        except:
            return False

    def get_activity(self, token, uid):
        signtoken = get_signtoken(
            '{"keyword":"","page":"1","token":"' + token + '","type":"1","uid":"' + uid + '","version":"4.2.6"}')
        str1 = '{"keyword":"","page":"1","signToken":"' + signtoken + '","token":"' + token + '","type":"1","uid":"' + uid + '","version":"4.2.6"}'
        data = "dataKey=t%2BZ88oeo2xscPIEBzd1JWLr%2Faae06xI9WOwwXOVRupB%2BsAsl1nj2HDpZPc3ygHRlgm0glZajSvF7FsxbGiBe%2FcykCvyhloLZfYPGGLrCZV6ZBVDBHgwg6%2Fkq87A6A%2Bp%2BmTeUyp3eZz4voIGytVkwmlofr0Jn5bgBOitzBJtnq0I%3D&data={}".format(
            jiami(str1))
        url = 'https://appdmkj.5idream.net/v2/activity/mime/list'
        res = requests.post(url, headers=self.headers, data=data)
        # print(res.text)
        try:
            return res.json()
        except:
            return False

    def get_cancle(self, signUpId, token, uid):
        signtoken = get_signtoken(
            '{"signUpId":"' + signUpId + '","token":"' + token + '","uid":"' + uid + '","version":"4.2.6"}')
        str1 = '{"signToken":"' + signtoken + '","signUpId":"' + signUpId + '","token":"' + token + '","uid":"' + uid + '","version":"4.2.6"}'
        data = "dataKey=t%2BZ88oeo2xscPIEBzd1JWLr%2Faae06xI9WOwwXOVRupB%2BsAsl1nj2HDpZPc3ygHRlgm0glZajSvF7FsxbGiBe%2FcykCvyhloLZfYPGGLrCZV6ZBVDBHgwg6%2Fkq87A6A%2Bp%2BmTeUyp3eZz4voIGytVkwmlofr0Jn5bgBOitzBJtnq0I%3D&data={}".format(
            jiami(str1))
        url = 'https://appdmkj.5idream.net/v2/signup/cancel'
        res = requests.post(url, headers=self.headers, data=data)
        # {"actionSheet":{"content":"数据出现异常了，请重新操作。","type":0},"code":"-2000","msg":"数据出现异常了，请重新操作。"}
        try:
            return res.json()
        except:
            return False


def test_token(path):
    a = Post()
    try:
        with open(path+'/'+'a.ini', 'r', encoding='utf-8') as f:
            token = f.readline().rstrip()
            name = f.readline().rstrip()
            uid = f.readline().rstrip()
            res = a.get_ids(token, uid)
            if res['code'] == '100':
                print("test_token：token还能用")
                return True
            else:
                return False
    except:
        print("test_token读取a.ini文件发生了异常")

