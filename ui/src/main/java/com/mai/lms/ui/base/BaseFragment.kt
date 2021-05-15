package com.mai.lms.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mai.lms.ui.exceptions.ExceptionInfo
import com.mai.lms.ui.extensions.showErrorAlertDialog
import moxy.MvpAppCompatFragment
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    protected abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun showErrorAlert(info: ExceptionInfo) {
        context?.showErrorAlertDialog(info)
    }

    override fun showErrorAlert(titleRes: Int, msgRes: Int) {
        context?.showErrorAlertDialog(msgRes, titleRes)
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
interface BaseView : MvpView{

    fun showErrorAlert(info: ExceptionInfo)

    fun showErrorAlert(titleRes: Int, msgRes: Int)
}
