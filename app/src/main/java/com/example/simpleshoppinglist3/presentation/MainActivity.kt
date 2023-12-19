package com.example.simpleshoppinglist3.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.BuildConfig
import com.example.simpleshoppinglist3.R
import com.example.simpleshoppinglist3.databinding.CustomConfirmDialogViewBinding
import com.example.simpleshoppinglist3.databinding.CustomHelpDialogViewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections


const val APP_PREFERENCES = "APP_PREFERENCES"
const val PREF_FIRST_START_APP = "1"

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    private var baskPressedTime: Long = 0
    private var backToast: Toast? = null

    private lateinit var preferences: SharedPreferences

    private lateinit var customToolbarMenu: Toolbar

    private lateinit var enableStateShopList: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        customToolbar = findViewById(R.id.toolbar)
//        customToolbar.title = ""
//        setSupportActionBar(customToolbar)

        setupRecyclerView()
        initCustomToolbarMenu()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }

        viewModel.enableStateShopItemList.observe(this) {
            enableStateShopList = it
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

        checkFirstStartApp()
    }

    private fun initCustomToolbarMenu() {
        customToolbarMenu = findViewById(R.id.toolbar)
        customToolbarMenu.title = ""
        setSupportActionBar(customToolbarMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val onPressItemId = item.itemId

        if (onPressItemId == R.id.delete_all_shop_item) {
            showConfirmDialog(onPressItemId)
        }
        if (onPressItemId == R.id.enable_all_shop_item) {
            showConfirmDialog(onPressItemId)
        }
        if (onPressItemId == R.id.disable_all_shop_item) {
            showConfirmDialog(onPressItemId)
        }
        if (onPressItemId == R.id.send_item) {
            showConfirmDialog(onPressItemId)
        }
        return true
    }

    private fun checkFirstStartApp() {

        preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        val value = preferences.getString(PREF_FIRST_START_APP, APP_FIRS_START_TRUE)
        if (value == APP_FIRS_START_TRUE) {
            preferences.edit()
                .putString(PREF_FIRST_START_APP, APP_FIRS_START_FALSE)
                .apply()

            showHelpDialog()
        } else {
            //showHelpDialog()
            return
        }
    }

    private fun showConfirmDialog(onPressItemId: Int) {

        val itemList = shopListAdapter.currentList

        val dialogBinding = CustomConfirmDialogViewBinding.inflate(layoutInflater)

        if (onPressItemId == R.id.send_item) {
            viewModel.getEnableStateShopItemList(itemList)
            setShopItemListToMessengers()
        } else {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setView(dialogBinding.root)
            val dialog = builder.create()
            when (onPressItemId) {
                R.id.enable_all_shop_item ->
                    dialogBinding.etTextMessage.text = getText(R.string.all_item_enable)

                R.id.disable_all_shop_item ->
                    dialogBinding.etTextMessage.text = getText(R.string.all_item_disable)

                R.id.delete_all_shop_item ->
                    dialogBinding.etTextMessage.text = getText(R.string.all_item_delete)

                R.id.send_item ->
                    dialogBinding.etTextMessage.text = "dssdfgsdfg"
            }
            Log.d("MyLog111", onPressItemId.toString())
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialogBinding.btnConfirm.setOnClickListener {
                if (onPressItemId == R.id.enable_all_shop_item) {
                    viewModel.changeAllEnableState(itemList)
                }
                if (onPressItemId == R.id.disable_all_shop_item) {
                    viewModel.changeAllDisableState(itemList)
                }
                if (onPressItemId == R.id.delete_all_shop_item) {
                    viewModel.deleteAllShopItem(itemList)
                }
                dialog.dismiss()
            }
            dialogBinding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun setShopItemListToMessengers() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            enableStateShopList + APP_LINK
        )
        startActivity(Intent.createChooser(intent, getString(R.string.send_items_list)))
    }


    private fun showHelpDialog() {
        val dialogBinding = CustomHelpDialogViewBinding.inflate(layoutInflater)
        //val view = View.inflate(this@MainActivity, R.layout.dialog_view, null)

        val builder = AlertDialog.Builder(this@MainActivity)
        //builder.setView(view)
        builder.setView(dialogBinding.root)

        val dialog = builder.create()
        dialog.show()
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

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

    companion object {

        private const val APP_FIRS_START_TRUE = "1"
        private const val APP_FIRS_START_FALSE = "0"
        private const val APP_LINK = "https://apps.rustore.ru/app/com.example.simpleshoppinglist3"
    }
}