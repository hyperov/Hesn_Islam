package com.islam.hesn.myapplication.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.islam.hesn.myapplication.R
import kotlinx.android.synthetic.main.fragment_contact_us_bottom_sheet.*

class ContactUsBottomSheetFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.fragment_contact_us_bottom_sheet,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        sendButton.setOnClickListener { dismissAllowingStateLoss() }
    }

    companion object {
        @JvmStatic
        fun newInstance(): ContactUsBottomSheetFragment {
            return ContactUsBottomSheetFragment()
        }
    }
}