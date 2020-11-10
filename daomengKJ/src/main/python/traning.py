import time
import unittest
from datetime import datetime

def  java(java_object):
    java_object.showinfo(title="xxx",mess="xxxxjjfd")
    print("testjava_object")

class MyTestCase(unittest.TestCase):
    def test_something(self):
        self.assertEqual(True, False)

    def test_datetime(self):
        # 应该这里是报名
        print("开始报名ing ")
        start_time = datetime(2020, 11, 5, 19, 49)  # 用 指定日期时间创建datetime
        now_time = datetime.now()  # 现在的时间
        sleep_time = start_time - now_time  # 时间差
        print('将睡眠秒', sleep_time.seconds + 1.08)
        time.sleep(sleep_time.seconds + 1.08)  # 单位为秒 ,延迟 80毫秒 阻塞  . 加1秒
        # 格式化成2016-03-20 11:45:39形式
        print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
        print(time.time())

    def test_chang(self):
        # 类作为参数传递
        cl = me()
        print("之后 ")
        cl.change()



if __name__ == '__main__':
    unittest.main()


class arr:
    start = 'a'

    def something(self):
        print(self.start)


class me(arr):
    def change(self):
        self.start = 'b'
        print(self.start)
