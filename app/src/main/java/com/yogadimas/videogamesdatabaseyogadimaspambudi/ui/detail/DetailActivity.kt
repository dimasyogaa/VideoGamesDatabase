package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.yogadimas.videogamesdatabaseyogadimaspambudi.R
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.GameDetailResponse
import com.yogadimas.videogamesdatabaseyogadimaspambudi.databinding.ActivityDetailBinding
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.factory.ViewModelFactory
import com.yogadimas.videogamesdatabaseyogadimaspambudi.util.Resource
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(KEY_EXTRA_ID, 0)

        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
        }



        getDetailGame()
    }

    private fun getDetailGame() {
        detailViewModel.getDetailGame(id).observe(this) {
            when (it) {
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> showLoading(true)
                is Resource.Success ->  {
                    showLoading(false)
                        setGame(it.data)
                }
            }
        }
    }

    private fun setGame(it: GameDetailResponse?) {
        binding.apply {
            ivGame.load(it?.backgroundImage) {
                crossfade(true)
                placeholder(R.drawable.placholder_image)
                error(R.drawable.error_image)
            }
            tvPublisher.text = it?.publishers?.first()?.name
            tvName.text = it?.name
            tvDateRelease.text = it?.released
            tvRate.text = it?.rating.toString().toDouble().toString()
            tvDesc.text = it?.descriptionRaw
        }
    }

    private fun showLoading(boolean: Boolean) {
        binding.mainProgressBar.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    companion object {
        const val KEY_EXTRA_ID = "key_extra_id"
    }
}