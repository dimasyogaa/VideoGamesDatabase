package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yogadimas.videogamesdatabaseyogadimaspambudi.databinding.ActivityDetailBinding
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(KEY_EXTRA_ID, 0)

        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
        }



    }

    private fun getGameDetail() {
        TODO("Not yet implemented")
    }

    companion object {
        const val KEY_EXTRA_ID = "key_extra_id"
    }
}