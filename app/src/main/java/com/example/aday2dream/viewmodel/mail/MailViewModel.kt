package com.example.aday2dream.viewmodel.mail

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aday2dream.model.repository.MailRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream


class MailViewModel(
    private val repository: MailRepository
) : ViewModel() {

    fun sendEmail(
        context: Context,
        recipientEmail: String,
        subject: String,
        body: String,
        fileUri: Uri,
        onSuccess: (String?) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("Mail View Model","Entered Mail View Model")
                val contentResolver = context.contentResolver
                val fileDescriptor = contentResolver.openFileDescriptor(fileUri, "r") ?: return@launch
                val inputStream = FileInputStream(fileDescriptor.fileDescriptor)


                val fileName: String = contentResolver.query(fileUri, null, null, null, null)?.use { cursor ->
                    getFileName(cursor)
                } ?: "attachment.mp3"

                val file = File(context.cacheDir, fileName)

                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                val requestFile = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("audioFile", file.name, requestFile)

                val toPart = recipientEmail.toRequestBody("text/plain".toMediaTypeOrNull())
                val subjectPart = subject.toRequestBody("text/plain".toMediaTypeOrNull())
                val textPart = body.toRequestBody("text/plain".toMediaTypeOrNull())

                Log.d("MailViewModel", "Sending email to: $recipientEmail with attachment: $fileName")

                val response = repository.sendMail(toPart, subjectPart, textPart, filePart)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("MailViewModel", "Email sent successfully: $responseBody")
                    onSuccess(responseBody)
                } else {
                    Log.e("MailViewModel", "Email sending failed: ${response.message()}")
                    onError(response.message() ?: "Unknown error")
                }
            } catch (e: Exception) {
                Log.e("MailViewModel", "Error sending email: ${e.localizedMessage}")
                onError("Error occurred while sending email")
            }
        }
    }

    private fun getFileName(cursor: Cursor): String? {
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        return if (nameIndex != -1) {
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        } else {
            null
        }
    }
}

