package com.islam.hesn.myapplication.contactus

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islam.hesn.myapplication.R
import androidx.core.net.toUri
import ui.theme.ColorAccent
import ui.theme.ColorPrimary
import ui.theme.JanaFamily

@Composable
fun ContactUsComposableBottomSheet(onDismiss: () -> Unit) {
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var subjectError by remember { mutableStateOf<String?>(null) }
    var messageError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    fun sendEmail(subject: String, message: String) {
        val mailto = "mailto:hosenalislam@gmail.com,a.ahmed.nabil90@gmail.com?" +
                "subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(message)

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = mailto.toUri()
        }
        context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(color = ColorAccent, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                text = stringResource(id = R.string.contact_us),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                fontFamily = JanaFamily,
                fontSize = 32.sp,
                color = colorResource(id = R.color.colorPrimaryDark),
                textAlign = TextAlign.Center
            )
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.email_title),
                    modifier = Modifier
                        .wrapContentWidth(),
                    fontFamily = JanaFamily,
                    fontSize = dimensionResource(id = R.dimen.text_size_16).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.colorPrimaryDark),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = subject,
                    onValueChange = {
                        subject = it
                        subjectError = null
                    },
                    label = { Text(stringResource(R.string.title)) },
                    isError = subjectError != null,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            subjectError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.your_message),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding( start = 16.dp),
                fontFamily = JanaFamily,
                fontSize = dimensionResource(id = R.dimen.text_size_24).value.sp,
                color = colorResource(id = R.color.colorPrimaryDark),
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                    messageError = null
                },
                label = { Text(stringResource(R.string.write_your_message_here)) },
                isError = messageError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp)
            )
            messageError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val titleError = stringResource(R.string.email_title_error)
            val messageEmptyError = stringResource(R.string.message_empty_error)
            ElevatedButton(
                onClick = {
                    val isSubjectEmpty = subject.isBlank()
                    val isMessageEmpty = message.isBlank()

                    subjectError =
                        if (isSubjectEmpty) titleError else null
                    messageError =
                        if (isMessageEmpty) messageEmptyError else null

                    if (!isSubjectEmpty && !isMessageEmpty) {
                        sendEmail(subject, message)
                        onDismiss()
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(R.string.send), fontFamily = JanaFamily, fontSize = 16.sp)
            }
        }
    }
}

@Preview
@Composable
fun ContactUsComposableBottomSheetPreview() {
    ContactUsComposableBottomSheet(
        onDismiss = {},
    )
}
