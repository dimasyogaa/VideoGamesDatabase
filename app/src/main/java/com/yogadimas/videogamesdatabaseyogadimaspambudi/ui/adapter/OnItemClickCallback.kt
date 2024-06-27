package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.adapter



fun interface OnItemClickCallback<T> {
    fun onItemClicked(data: T)
}