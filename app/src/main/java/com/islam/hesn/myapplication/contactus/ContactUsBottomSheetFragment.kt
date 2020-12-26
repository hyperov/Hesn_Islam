package com.islam.hesn.myapplication.contactus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "ContactUsBottomSheetFragment")
        setupViews()
    }

    private fun setupViews() {
        setTypingListeners()
        sendButton.setOnClickListener {
            sendButtonClick()
            checkForErrors()
        }
    }

    private fun setTypingListeners() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    message.error = null
                    subject.error = null
                }
            }
        }

        etMessage.addTextChangedListener(textWatcher)
        etSubject.addTextChangedListener(textWatcher)
    }

    private fun sendButtonClick() {
        if (checkForErrors()) {
            sendEmail()
            dismissAllowingStateLoss()
        }
    }

    private fun checkForErrors(): Boolean {

        if (etMessage.text?.isEmpty()!!) message.error = getString(R.string.message_empty_error)
        if (etSubject.text?.isEmpty()!!) subject.error = getString(R.string.email_title_error)

        return etMessage.text?.isNotEmpty()!! && etSubject.text?.isNotEmpty()!!
    }

    private fun sendEmail() {
        val emailSubject = etSubject.text.toString()
        val emailMessage = etMessage.text.toString()

        val mailto = "mailto:hosenalislam@gmail.com?" +
                "subject=" + emailSubject +
                "&body=" + emailMessage

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse(mailto)

        startActivity(emailIntent)
    }

    companion object {
        @JvmStatic
        fun newInstance(): ContactUsBottomSheetFragment {
            return ContactUsBottomSheetFragment()
        }
    }
}