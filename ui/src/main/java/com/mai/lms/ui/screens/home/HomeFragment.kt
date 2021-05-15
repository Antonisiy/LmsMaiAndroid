package com.mai.lms.ui.screens.home

import android.os.Bundle
import android.view.View
import com.mai.lms.ui.R
import com.mai.lms.ui.base.BaseFragment
import com.mai.lms.ui.exceptions.ExceptionInfo
import com.mai.lms.ui.extensions.hideKeyboard
import com.mai.lms.ui.extensions.showErrorAlertDialog
import kotlinx.android.synthetic.main.fragment_home.*

import moxy.presenter.InjectPresenter

class HomeFragment : BaseFragment(), HomeView {

    @InjectPresenter
    internal lateinit var presenter: HomePresenter

    override fun layoutId(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.hideKeyboard()
        init()
    }

    private fun init() {
        button_one.setOnClickListener { presenter.onNextClick() }
    }

    override fun showErrorAlert(info: ExceptionInfo) {
        context?.showErrorAlertDialog(info)
    }

}