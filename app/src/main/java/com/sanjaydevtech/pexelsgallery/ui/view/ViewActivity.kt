package com.sanjaydevtech.pexelsgallery.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.sanjaydevtech.pexelsgallery.databinding.ActivityViewBinding
import com.sanjaydevtech.pexelsgallery.model.Photo
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import android.content.Intent
import androidx.core.content.FileProvider

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val photo = intent.getParcelableExtra<Photo>("photo") ?: run {
            finish()
            return
        }
        binding.previewImage.load(photo.src.medium)
        val filename = "share-image-${System.currentTimeMillis()}.png"
        binding.shareButton.setOnClickListener {
            val drawable = binding.previewImage.drawable
            if (drawable !is BitmapDrawable) return@setOnClickListener
            val bitmap = drawable.bitmap
            try {
                openFileOutput(filename, Context.MODE_PRIVATE).use {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, it)
                }
                val file = File(filesDir, filename)
                val uri = FileProvider.getUriForFile(
                    this,
                    "$packageName.fileprovider",
                    file
                )
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.putExtra(Intent.EXTRA_TEXT, "Photo by ${photo.photographer} on Pexels\n${photo.photographerUrl}")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(Intent.createChooser(intent, "Share"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getBitmap(url: String): Bitmap? {
        val loader = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(url)
            .build()
        return (loader.execute(request).drawable as? BitmapDrawable)?.bitmap
    }
}