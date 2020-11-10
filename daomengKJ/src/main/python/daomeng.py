from java import jclass
from jiami import *
from test import Post, test_token
import os

path = ''

class Main(Post):
    """类的帮助信息"""  # 类文档字符串
    acc = ''
    pwd = ''
    path = ''
    instance = None
    def __init__(self):  # 构造函数 如果子类定义了自己的初始化函数，而在子类中没有显示调用父类的初始化函数，则父类的属性不会被初始化
        print("call __init__ from Child class")
        super(Main,self).__init__()   #要将子类Child和self传递进去
        java__static = jclass("com.zhu.daomengkj.Py_invoke_Java")
        self.instance=java__static.INSTANCE
        account=self.instance.get_account()
        self.acc=account.get("username")
        self.pwd=account.get("password")
        self.path=self.instance.get_path() # android内部存储文件路径
        print(self.acc,self.pwd,self.path)

    def read(self):
        with open(self.path+'/'+'a.ini', 'r', encoding='utf-8') as f:
            self.token = f.readline().rstrip()
            self.name = f.readline().rstrip()
            self.uid = f.readline().rstrip()

    def login(self):
        print("文件ini是否存在",os.path.exists(self.path+'/'+'a.ini'))
        if os.path.exists(self.path+'/'+'a.ini'):
            if test_token(self.path):
                return True
            else:
                self.instance.showwarning('出错了', '登录失效，请重新登录')
                os.remove(self.path+'/'+'a.ini')
                print("删除",self.path,"a.ini文件,重新登录，将新生成")
                return False
        else:
            if get_token(self.acc, self.pwd,self.path):
                return True
            else:
                self.instance.showwarning('出错了',  '请检查账号密码')
                return False

    def get_id(self):
        self.get_ids(self.token, self.uid) # 仅我可以报名的活动
        self.instance.showinfo('欢迎您', self.name)
        names = []
        for name, id, statusText in zip(self.names, self.ids, self.statusTexts):
            names.append(name + '   {}   {}'.format(id, statusText))
        return names

    def can_join(self):
        names = ['可报名活动']
        if self.get_can_join(self.token, self.uid):
            print("打印self.names, self.ids, self.statusTexts",self.names, self.ids, self.statusTexts)
            for name, id, statusText in zip(self.names, self.ids, self.statusTexts):
                names.append(name + '   {}   {}'.format(id, statusText))
            print("can_join中的names",names)
            return names
        else:
            self.instance.showwarning('出错了', '没有活动')

    def chiken(self):
        id = self.instance.get_id1()
        res = self.get_info(id, self.token, self.uid)
        if res:
             return res
        else:
            self.instance.showwarning('出错了', '查询失败，请检查活动id')

    def get_start_time(self):
        to_join_act_id = self.instance.get_id2()  # 活动id
        print("方法能调用 ？",self.get_info)

        res = self.get_info(to_join_act_id, self.token, self.uid)
        print("get_start_time中的res 可能返回了false",res)
        if res:
            text = res['data']['joindate']  # 报名时间
            text = text.split('-', 1)[0]  # split() 通过指定分隔符对字符串进行切片 第二个参数为 1，返回两个参数列表
            return text
        else:
            self.instance.showwarning('出错了', '查询报名时间，请检查活动id')
            return False

    def enter(self):
        id = self.instance.get_id2()
        print("报名的id为",id)
        start_time = self.get_start_time()  # post 类的 属性 start_time 赋值 ，保存这个值
        print("start_time为", start_time)

        res = self.join(id, self.token, self.uid,start_time)
        if res:
            if res['code'] == '100':
                self.instance.showinfo('报名详情', '报名成功')
            else:
                self.instance.showinfo('报名详情', res['msg'])
        else:
            self.instance.showwarning('出错了', '查询失败，请检查id')

    def get_joined(self):
        res = self.get_activity(self.token, self.uid)
        names = []
        ids = []
        heights = ['已报名活动']
        if res:
            for li in res['data']['list']:
                if li['statusText'] == '报名中':
                    names.append(li['name'])
                    ids.append(li['aid'])
            if names:
                for name, id in zip(names, ids):
                    heights.append(name + '   {}'.format(id))
                return names
            else:
                self.instance.showwarning('出错了', '没有已报名活动')

    def concle(self):
        id = self.instance.get_id3()
        res = self.get_info(id, self.token, self.uid)
        if res:
            signUpId = str(res['data']['signUpId'])
            if self.get_cancle(signUpId, self.token, self.uid)['code'] == '100':
                self.instance.showinfo('成功', '取消报名成功')
        else:
            self.instance.showwarning('出错了', '失败，请检查活动id')


main = Main()


def login():
    if main.login():
        print("登录成功了")
        main.read()
        return main.get_id()
    else:
        return None


def chiken():
    if main.login():
        main.read()
        main.chiken()
    else:
        pass


def join():
    if main.login():
        main.read()  # 读取最新的 token
        main.enter()
    else:
        pass


def can_join():
    if main.login():
        main.read()
        return main.can_join()
    else:
        pass


def joined():
    if main.login():
        main.read()
        main.get_joined()
    else:
        pass


def concle():
    if main.login():
        main.read()
        main.concle()
    else:
        pass

