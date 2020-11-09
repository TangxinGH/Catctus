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
    def __init__(self):  # 构造函数
        java__static = jclass("com.zhu.daomengkj.Py_invoke_Java")
        self.instance=java__static.INSTANCE
        account=self.instance.get_account()
        self.acc=account.get("username")
        self.pwd=account.get("password")
        self.path=self.instance.get_path() # android内部存储文件路径

    def read(self):
        with open(self.path+'/'+'a.ini', 'r', encoding='utf-8') as f:
            self.token = f.readline().rstrip()
            self.name = f.readline().rstrip()
            self.uid = f.readline().rstrip()

    def login(self):
        if os.path.exists(self.path+'/'+'a.ini'):
            if test_token(self.path):
                return True
            else:
                self.instance.showwarning(title='出错了', message='登录失效，请重新登录')
                os.remove(self.path+'/'+'a.ini')
                return False
        else:
            if get_token(self.acc, self.pwd,self.path):
                return True
            else:
                self.instance.showwarning(title='出错了', message='请检查账号密码')
                return False

    def get_id(self):
        self.get_ids(self.token, self.uid)
        self.instance.showinfo('欢迎您', self.name)
        names = []
        for name, id, statusText in zip(self.names, self.ids, self.statusTexts):
            names.append(name + '   {}   {}'.format(id, statusText))
        return names

    def can_join(self):
        names = ['可报名活动']
        if self.get_can_join(self.token, self.uid):
            for name, id, statusText in zip(self.names, self.ids, self.statusTexts):
                names.append(name + '   {}   {}'.format(id, statusText))
            return names
        else:
            self.instance.showwarning('出错了', '没有活动')

    def chiken(self):
        id = self.instance.get_id1.get()
        res = self.get_info(id, self.token, self.uid)
        if res:
             return res
        else:
            self.instance.showwarning(title='出错了', message='查询失败，请检查活动id')

    def get_start_time(self):
        to_join_act_id = self.instance.get_id2.get()  # 活动id

        res = self.get_info(to_join_act_id, self.token, self.uid)
        if res:
            text = res['data']['joindate']  # 报名时间
            text = text.split('-', 1)[0]  # split() 通过指定分隔符对字符串进行切片 第二个参数为 1，返回两个参数列表
            return text
        else:
            self.instance.showwarning(title='出错了', message='查询报名时间，请检查活动id')
            return False

    def enter(self):
        id = self.instance.get_id2.get()
        self.start_time = self.get_start_time()  # post 类的 属性 start_time 赋值

        res = self.join(id, self.token, self.uid)
        if res:
            if res['code'] == '100':
                self.instance.showinfo(title='报名详情', message='报名成功')
            else:
                self.instance.showinfo(title='报名详情', message=res['msg'])
        else:
            self.instance.showwarning(title='出错了', message='查询失败，请检查id')

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
        id = self.instance.get_id3.get()
        res = self.get_info(id, self.token, self.uid)
        if res:
            signUpId = str(res['data']['signUpId'])
            if self.get_cancle(signUpId, self.token, self.uid)['code'] == '100':
                self.instance.showinfo(title='成功', message='取消报名成功')
        else:
            self.instance.showwarning(title='出错了', message='失败，请检查活动id')


main = Main()


def login():
    if main.login():
        main.read()
        main.get_id()
    else:
        None


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
        main.can_join()
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

