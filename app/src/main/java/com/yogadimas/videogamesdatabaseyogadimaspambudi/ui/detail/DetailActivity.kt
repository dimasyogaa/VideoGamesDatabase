package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.yogadimas.videogamesdatabaseyogadimaspambudi.R
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.entities.GameFavoriteEntity
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

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(KEY_EXTRA_ID, 0)

        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
        }

        getDetailGame()
        getIsFavoriteOrGetFavoriteGameById()
    }

    private fun getDetailGame() {
        detailViewModel.getDetailGame(id).observe(this) {
            when (it) {
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    binding.tvRate.visibility = View.VISIBLE
                    binding.tvFavorite.visibility = View.VISIBLE

                    setGame(it.data)
                }
            }
        }

    }

    private fun getIsFavoriteOrGetFavoriteGameById() {
        detailViewModel.getFavoriteGameById(id).observe(this) {
            try {
                if (!it.equals(null)) {
                    isFavorite = true
                    setFavoriteState(R.drawable.ic_favorite_selected, R.color.md_theme_error)
                }
            } catch (e: NullPointerException) {
                isFavorite = false
                setFavoriteState(R.drawable.ic_favorite_normal, R.color.md_theme_onSurface)
            }
        }

    }

    private fun setGame(game: GameDetailResponse?) {
        binding.apply {
            ivGame.load(game?.backgroundImage) {
                crossfade(true)
                placeholder(R.drawable.placholder_image)
                error(R.drawable.error_image)
            }
            tvPublisher.text = game?.publishers?.first()?.name
            tvName.text = game?.name
            tvDateRelease.text = game?.released
            tvRate.text = game?.rating.toString().toDouble().toString()
            tvDesc.text = game?.descriptionRaw
            tvFavorite.setOnClickListener {
                val gameFavorite = GameFavoriteEntity(
                    game?.id ?: 0,
                    game?.backgroundImage,
                    game?.name,
                    game?.released,
                    game?.rating.toString().toDouble(),
                    game?.descriptionRaw,
                )
                if ((isFavorite)) {
                    detailViewModel.deleteFavoriteGame(gameFavorite)
                } else {
                    detailViewModel.insertFavoriteGame(gameFavorite)
                }
            }
        }
    }

    private fun showLoading(boolean: Boolean) {
        binding.mainProgressBar.visibility = if (boolean) {
            binding.tvRate.visibility = View.GONE
            binding.tvFavorite.visibility = View.GONE
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setFavoriteState(drawable: Int, color: Int) {
        val textView = binding.tvFavorite
        val drawableLeft = ContextCompat.getDrawable(this, drawable)
        val colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(applicationContext, color),
            PorterDuff.Mode.SRC_IN
        )

        drawableLeft?.colorFilter = colorFilter
        textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
    }

    companion object {
        const val KEY_EXTRA_ID = "key_extra_id"
    }
}