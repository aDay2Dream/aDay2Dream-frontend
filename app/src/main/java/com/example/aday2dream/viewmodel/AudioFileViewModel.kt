package com.example.aday2dream.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Audio
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aday2dream.model.AudioFile
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.AudioFileDto
import com.example.aday2dream.model.repository.AudioFileRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream

class AudioFileViewModel(private val repository: AudioFileRepository) : ViewModel() {
    private val _audiofile = MutableLiveData<AudioFileDto?>()
    val audiofile: LiveData<AudioFileDto?> get() = _audiofile

    fun uploadAudio(
        context: Context,
        fileUri: Uri,
        title: String,
        duration: String,
        onSuccess: (AudioFileDto) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {

                val fileDescriptor = context.contentResolver.openFileDescriptor(fileUri, "r") ?: return@launch
                val inputStream = FileInputStream(fileDescriptor.fileDescriptor)

                val sanitizedTitle = title.replace("[^a-zA-Z0-9]".toRegex(), "_") // Replace invalid characters with underscores
                val fileName = "$sanitizedTitle.mp3"
                val file = File(context.cacheDir, fileName)

                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                // Prepare the file part
                val requestFile = file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                // Prepare the title and duration parts
                val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
                val durationPart = duration.toRequestBody("text/plain".toMediaTypeOrNull())
                Log.d("Upload", titlePart.toString() )
                Log.d(  "Upload", durationPart.toString() )
                Log.d("Upload", filePart.toString())
                // Make the API call
                val response = repository.uploadAudio(filePart, titlePart, durationPart)
                if (response.isSuccessful) {
                    val uploadedAudioFile = response.body() ?: return@launch
                    _audiofile.postValue(uploadedAudioFile) // Store the actual AudioFile
                    onSuccess(uploadedAudioFile)
                    Log.d("Upload", "Success: ${uploadedAudioFile.toString()}")
                } else {
                    Log.d("Upload", response.toString())
                    Log.e("Upload", "Failed: ${response.message()}")
                    onError(response.message() ?: "Unknown error")
                }
            } catch (e: Exception) {
                Log.e("Upload", "Error: ${e.localizedMessage}")
                onError("Error occurred during upload")
            }
        }
    }
}
