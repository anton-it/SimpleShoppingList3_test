package com.example.simpleshoppinglist3.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleshoppinglist3.R
import com.example.simpleshoppinglist3.databinding.CustomDialogViewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    private var baskPressedTime: Long = 0
    private var backToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {

            shopListAdapter.submitList(it)

        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }

        val list = listOf(0, 1, 2, 3, 4, 5)
        Log.d("MyLogg", "Collection 1 $list")
        Collections.swap(list, 0, 5)
        Log.d("MyLogg", "Collection 2 $list")

        showHelpDialog()

    }

    private fun showHelpDialog() {
        val dialogBinding = CustomDialogViewBinding.inflate(layoutInflater)
        //val view = View.inflate(this@MainActivity, R.layout.dialog_view, null)

        val builder = AlertDialog.Builder(this@MainActivity)
        //builder.setView(view)
        builder.setView(dialogBinding.root)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnConfirm.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onBackPressed() {
        if (baskPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            return
        } else {
            backToast =
                Toast.makeText(baseContext, R.string.exit_message, Toast.LENGTH_SHORT)
            backToast?.show()
        }
        baskPressedTime = System.currentTimeMillis()
    }

    override fun onEditingFinished() {
//        Так как при перевороте экрана функционал размещения одновременно Activity и Fragment отключен
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//        supportFragmentManager.popBackStack()
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        rvShopList.adapter = shopListAdapter

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(

            //ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val shopList = shopListAdapter.currentList
                val sourcePosition = viewHolder.adapterPosition
                val targetPosition = target.adapterPosition

                viewModel.moveShopItem(sourcePosition, targetPosition)

               //Collections.swap(shopList, sourcePosition, targetPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            Log.d("MyLog111", "You click shop item id: ${it.id}")

            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }

    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListenerL = {
            viewModel.changeEnableState(it)
        }
    }
}