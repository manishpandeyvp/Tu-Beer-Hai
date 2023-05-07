package com.example.tubeerhai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tubeerhai.data.model.BeerModel
import com.example.tubeerhai.presentation.BeersViewModel
import com.example.tubeerhai.presentation.adapter.BeersListAdapter
import com.example.tubeerhai.util.Share
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifImageView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val beersViewModel: BeersViewModel by viewModels()
    private lateinit var rvBeerList: RecyclerView
    private lateinit var gifLoading: GifImageView
    private lateinit var errorImage: ImageView
    private lateinit var errorText: TextView
    private var beersList: List<BeerModel> = emptyList()
    private lateinit var adapter: BeersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBeerList = findViewById(R.id.rv_beers_list)
        gifLoading = findViewById(R.id.gif_loading)
        errorImage = findViewById(R.id.iv_error)
        errorText = findViewById(R.id.tv_error)

        adapter = BeersListAdapter(this, beersList)
        setUpRecyclerView()

        beersViewModel.callGetBeerListApi()
        postFetchingBeersList()
    }

    private fun postFetchingBeersList() {
        CoroutineScope(Dispatchers.Main).launch {
            beersViewModel.getBeerList().collect { value ->
                when {
                    value.isLoading -> {
                        onLoadingVisibility()
                    }
                    value.error.isNotBlank() -> {
                        onErrorVisibility()
                    }
                    value.beersList.isEmpty().not() -> {
                        onSuccessVisibility()
                        updateBeersListUi(value.beersList)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        rvBeerList.layoutManager = LinearLayoutManager(this)
        rvBeerList.adapter = adapter
        adapter.setOnClickListener(object : BeersListAdapter.OnClickListener {
            override fun onClick(position: Int, model: BeerModel) {
                Share.directWhatsAppShare(model, this@MainActivity)
            }

            override fun onLongClick(position: Int, model: BeerModel) {
                showBottomSheet(model)
            }
        })
    }

    private fun updateBeersListUi(list: List<BeerModel>) {
        if (list.isNotEmpty()) {
            beersList = list
            rvBeerList.visibility = View.VISIBLE
            adapter.setData(beersList)
        }
    }

    private fun showBottomSheet(model: BeerModel) {
        Log.d("list_debug_bs: ", "$model")
        val bottomSheet: BottomSheetDialog = BottomSheetDialog(this)
        bottomSheet.setContentView(R.layout.bs_beer_layout)
        bottomSheet.dismissWithAnimation = true
        bottomSheet.setCancelable(true)
        bottomSheet.show()
    }

    private fun onErrorVisibility() {
        errorImage.visibility = View.VISIBLE
        errorText.visibility = View.VISIBLE
        gifLoading.visibility = View.GONE
        rvBeerList.visibility = View.GONE
    }

    private fun onLoadingVisibility() {
        errorImage.visibility = View.GONE
        errorText.visibility = View.GONE
        gifLoading.visibility = View.VISIBLE
        rvBeerList.visibility = View.GONE
    }

    private fun onSuccessVisibility() {
        errorImage.visibility = View.GONE
        errorText.visibility = View.GONE
        gifLoading.visibility = View.GONE
    }
}