package com.cj.customwidget.page.viewmodelv2

import androidx.lifecycle.*

val vMStores = HashMap<String, VMStore>()

inline fun <reified VM : ViewModel> LifecycleOwner.shareViewModels(
    scopeName:String,
    factory: ViewModelProvider.Factory?=null
):Lazy<VM> {
    val store: VMStore
    if (vMStores.keys.contains(scopeName)) {
        store = vMStores[scopeName]!!
    } else {
        store = VMStore()
        vMStores[scopeName] = store
    }
    store.register(this)
    return ViewModelLazy(VM::class,{store.viewModelStore},{factory?:ViewModelProvider.NewInstanceFactory()})
}

class VMStore : ViewModelStoreOwner {

    private val bindTargets = ArrayList<LifecycleOwner>()
    private var vmStore: ViewModelStore? = null

    fun register(host: LifecycleOwner) {
        if (!bindTargets.contains(host)) {
            bindTargets.add(host)
            host.lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        host.lifecycle.removeObserver(this)
                        bindTargets.remove(host)
                        if (bindTargets.isEmpty()) {//如果当前商店没有关联对象，则释放资源
                            vMStores.entries.find { it.value == this@VMStore }?.also {
                                vmStore?.clear()
                                vMStores.remove(it.key)
                            }
                        }
                    }
                }
            })
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        if (vmStore == null)
            vmStore = ViewModelStore()
        return vmStore!!
    }
}