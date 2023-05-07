package com.example.tubeerhai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tubeerhai.data.model.BeerModel
import com.example.tubeerhai.databinding.ActivityMainBinding
import com.example.tubeerhai.presentation.BeerDetailsBottomSheet
import com.example.tubeerhai.presentation.BeersViewModel
import com.example.tubeerhai.presentation.adapter.BeersListAdapter
import com.example.tubeerhai.util.Share
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val beersViewModel: BeersViewModel by viewModels()
    private var beersList: List<BeerModel> = emptyList()
    private lateinit var adapter: BeersListAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        rvBeerList = findViewById(R.id.rv_beers_list)
//        gifLoading = findViewById(R.id.gif_loading)
//        errorImage = findViewById(R.id.iv_error)
//        errorText = findViewById(R.id.tv_error)

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
        binding.rvBeersList.layoutManager = LinearLayoutManager(this)
        binding.rvBeersList.adapter = adapter
        adapter.setOnClickListener(object : BeersListAdapter.OnClickListener {
            override fun onClick(position: Int, model: BeerModel) {
                Share.directWhatsAppShare(model, this@MainActivity)
            }

            override fun onLongClick(position: Int, model: BeerModel) {
                BeerDetailsBottomSheet.newInstance(model).show(supportFragmentManager, "")
//                showBottomSheet(model)
            }
        })
    }

    private fun updateBeersListUi(list: List<BeerModel>) {
        if (list.isNotEmpty()) {
            beersList = list
            binding.rvBeersList.visibility = View.VISIBLE
            adapter.setData(beersList)
        }
    }

    private fun showBottomSheet(model: BeerModel) {
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(R.layout.bs_beer_layout)
        bottomSheet.dismissWithAnimation = true
        bottomSheet.setCancelable(true)
        bottomSheet.show()
    }

    private fun onErrorVisibility() {
        binding.ivError.visibility = View.VISIBLE
        binding.tvError.visibility = View.VISIBLE
        binding.gifLoading.visibility = View.GONE
        binding.rvBeersList.visibility = View.GONE
    }

    private fun onLoadingVisibility() {
        binding.ivError.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.gifLoading.visibility = View.VISIBLE
        binding.rvBeersList.visibility = View.GONE
    }

    private fun onSuccessVisibility() {
        binding.ivError.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.gifLoading.visibility = View.GONE
    }
}