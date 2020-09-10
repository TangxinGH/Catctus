package com.zhu.cactus.method

import kotlinx.coroutines.CancellationException
import java.lang.ref.WeakReference
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by zzy05 on 2017/8/26.
 * 弱引用实现类，用来实例化WeakReference
 *
 */
class WeakRef<T> internal constructor(any: T) {

    private val weakRef = WeakReference(any)

    suspend operator fun invoke(): T {
        //获取suspend函数中的当前实例，并暂停当前正在运行的协程或立即返回结果，而不会暂停
        return suspendCoroutineUninterceptedOrReturn {
            val ref = weakRef.get() ?: throw CancellationException()
            ref
        }
    }
}

class WeakReferenceDelegate<T>(initialValue: T? = null) : ReadWriteProperty<Any, T?> {
    var reference = WeakReference(initialValue)
        private set

    override fun getValue(thisRef: Any, property: KProperty<*>): T? = reference.get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        reference = WeakReference(value)
    }
}

/**
 * 给任何类型扩展函数weakReference() ，实例化WeakRef类并传入当前对象
 */
fun <T : Any> T.weakReference() = WeakRef(this)


fun <T> weak(initializer: () -> T) = Weak(initializer.invoke())

class Weak<T>(r: T) {
    private var reference: WeakReference<T?> = WeakReference(r)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = reference.get()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this.reference = WeakReference(value)
    }
}